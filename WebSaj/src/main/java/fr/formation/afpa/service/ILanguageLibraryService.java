package fr.formation.afpa.service;

import java.util.List;

import fr.formation.afpa.domain.LanguageLibrary;

public interface ILanguageLibraryService {

	public List<LanguageLibrary> findAll();
	public LanguageLibrary save(LanguageLibrary l);
	public LanguageLibrary findByNom(String l);
}
