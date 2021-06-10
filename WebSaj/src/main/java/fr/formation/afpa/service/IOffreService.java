package fr.formation.afpa.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import fr.formation.afpa.domain.Offre;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;

public interface IOffreService {
	public Offre save(Offre offre);

	public Integer findNbOffres(@Param("idTicket")Integer idTicket);
	
	List<Offre> findByTickets(Tickets ticket);

	Offre findByTicketsAndIntervenant(Tickets ticket,UserProfile intervenant);
	
	public void delete(Offre offre);
	
	public Integer countDistinctByIntervenant(UserProfile intervenant); 

	List <Offre> findTicketsOuvertsEtNonPerimes(@Param("idIntervenant") Integer idIntervenant);
	

}
