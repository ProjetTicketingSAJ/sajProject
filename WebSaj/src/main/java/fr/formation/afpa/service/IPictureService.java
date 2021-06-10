package fr.formation.afpa.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.domain.PictureDb;
import fr.formation.afpa.domain.UserProfile;

public interface IPictureService {

	public PictureDb save(UserProfile userProfile, MultipartFile file) throws IOException;

	public PictureDb findByUserProfileLike(UserProfile userProfile);
}
