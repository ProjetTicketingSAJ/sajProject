package fr.formation.afpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.afpa.domain.CodingLanguage;

@Repository
public interface ICodingLanguageDao extends JpaRepository<CodingLanguage, Integer> {
	
	public List<CodingLanguage> findAll();
	
}
