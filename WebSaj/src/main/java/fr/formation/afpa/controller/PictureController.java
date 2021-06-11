package fr.formation.afpa.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.formation.afpa.domain.PictureDb;
import fr.formation.afpa.domain.UserProfile;
import fr.formation.afpa.service.PictureService;
import fr.formation.afpa.service.UserService;

@EnableAutoConfiguration
@ComponentScan
@Controller
public class PictureController {

	PictureService pservice;
	UserService userService;

	public PictureController(PictureService pservice, UserService userService) {
		this.pservice = pservice;
		this.userService = userService;
	}

	@RequestMapping(value = "/image/{image_id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(Principal principal) throws IOException {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = loginedUser.getUsername();
		UserProfile user = userService.findByLogin(userInfo);

		PictureDb pdb = pservice.findByUserProfileLike(user);
		System.err.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* : " + pdb);
		byte[] imageContent = pdb.getImage();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}

}
