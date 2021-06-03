package fr.formation.afpa.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import fr.formation.afpa.domain.Offre;
import fr.formation.afpa.domain.Tickets;

public interface IOffreService {
	public Offre save(Offre offre);

	public Integer findNbOffres(@Param("idTicket")Integer idTicket);
	
	List<Offre> findByTickets(Tickets ticket);


}
