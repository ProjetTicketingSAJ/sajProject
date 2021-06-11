package fr.formation.afpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.afpa.dao.ICodingLanguageDao;
import fr.formation.afpa.domain.CodingLanguage;
import fr.formation.afpa.domain.UserProfile;

@Service
@Transactional
public class CodingLanguageService implements ICodingLanguageService {

	@Autowired
	private ICodingLanguageDao dao;

	public CodingLanguageService() {
	}

	@Override
	public List<CodingLanguage> findAll() {

		List<CodingLanguage> list = dao.findAll();

		return list;
	}

	@Override
	public List<CodingLanguage> findTop5ByUsersLike(UserProfile userprofile) {
		return dao.findTop5ByUsersLike(userprofile);
	}

}
