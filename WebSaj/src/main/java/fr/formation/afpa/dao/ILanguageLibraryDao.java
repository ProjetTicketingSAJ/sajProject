package fr.formation.afpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.afpa.domain.LanguageLibrary;

@Repository
public interface ILanguageLibraryDao extends JpaRepository<LanguageLibrary, Integer> {
	
	public List<LanguageLibrary> findAll();
	
	@SuppressWarnings("unchecked")
	public LanguageLibrary save(LanguageLibrary l);
	public LanguageLibrary findByNom(String l);
	
	
}
