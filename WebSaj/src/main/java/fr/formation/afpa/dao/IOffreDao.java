package fr.formation.afpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.formation.afpa.domain.Offre;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;

public interface IOffreDao extends JpaRepository<Offre, Integer> {

	@SuppressWarnings("unchecked")
	public Offre save(Offre offre);

	@Query(value = "select count(distinct id_offre) from offre where offre.ticket_vu = 0 and offre.id_ticket = :idTicket", nativeQuery = true)
	public Integer findNbOffres(@Param("idTicket") Integer idTicket);

	List<Offre> findByTickets(Tickets ticket);

	Offre findByTicketsAndIntervenant(Tickets ticket, UserProfile intervenant);

	public void delete(Offre offre);

	public Integer countDistinctByIntervenant(UserProfile intervenant);

	@Query(value = "select * from offre inner join tickets on tickets.id = offre.id_ticket where tickets.Statut='O' and id_intervenant= :idIntervenant group by tickets.ID", nativeQuery = true)
	List<Offre> findTicketsOuvertsEtNonPerimes(@Param("idIntervenant") Integer idIntervenant);

}
