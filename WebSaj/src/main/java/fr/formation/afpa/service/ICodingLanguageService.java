package fr.formation.afpa.service;

import java.util.List;

import fr.formation.afpa.domain.CodingLanguage;
import fr.formation.afpa.domain.UserProfile;

public interface ICodingLanguageService {

	public List<CodingLanguage> findAll();

	public List<CodingLanguage> findTop5ByUsersLike(UserProfile userprofile);

}
