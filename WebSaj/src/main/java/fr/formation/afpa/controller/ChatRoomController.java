package fr.formation.afpa.controller;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import fr.formation.afpa.domain.ChatMessage;

@Controller
public class ChatRoomController {

	private static final Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/chat/{roomId}/sendMessage")
	public void sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
		logger.info(roomId + " Chat message recieved is " + chatMessage.getContent());
		messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
	}

	@MessageMapping("/chat/{roomId}/addUser")
	public void addUser(@DestinationVariable String roomId, @Payload ChatMessage chatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
		if (currentRoomId != null) {
			ChatMessage leaveMessage = new ChatMessage();
			leaveMessage.setType(ChatMessage.MessageType.LEAVE);
			leaveMessage.setSender(chatMessage.getSender());
			messagingTemplate.convertAndSend(format("/chat-room/%s", currentRoomId), leaveMessage);
		}
		headerAccessor.getSessionAttributes().put("name", chatMessage.getSender());
		messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
	}
}