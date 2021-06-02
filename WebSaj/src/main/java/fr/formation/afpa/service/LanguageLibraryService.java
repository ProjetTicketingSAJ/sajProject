package fr.formation.afpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.afpa.dao.ILanguageLibraryDao;
import fr.formation.afpa.domain.LanguageLibrary;

@Service
@Transactional
public class LanguageLibraryService implements ILanguageLibraryService {
	@Autowired
	private ILanguageLibraryDao dao;

	public LanguageLibraryService() {
	}

	@Override
	public List<LanguageLibrary> findAll() {
		List<LanguageLibrary> list = dao.findAll();
		return list;
	}

	@Override
	public LanguageLibrary save(LanguageLibrary l) {
		dao.save(l);
		return l;
	}

	@Override
	public LanguageLibrary findByNom(String l) {
		
		return dao.findByNom(l);
	}
}
