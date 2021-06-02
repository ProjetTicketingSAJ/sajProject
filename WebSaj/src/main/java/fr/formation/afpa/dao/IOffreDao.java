package fr.formation.afpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.afpa.domain.Offre;

public interface IOffreDao extends JpaRepository<Offre, Integer> {

	@SuppressWarnings("unchecked")
	public Offre save(Offre offre);

}
