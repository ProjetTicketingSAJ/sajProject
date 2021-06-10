package fr.formation.afpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

//	registerStompEndpoints method is used to register a websocket endpoint
//	that the clients will use to connect to the server.
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sock").setAllowedOriginPatterns("*").withSockJS();
	}

//  configureMessageBroker method is used to configure our message broker 
//	which will be used to route messages from one client to another.
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/chat-room");
		registry.setApplicationDestinationPrefixes("/chat-app");
	}

}
