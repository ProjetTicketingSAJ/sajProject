
package fr.formation.afpa.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.domain.Tickets;

@Repository
public interface ITicketsDao extends JpaRepository<Tickets, Integer> {

	@SuppressWarnings("unchecked")
	public Tickets save(Tickets ticket);

	public List<Tickets> findAll();

	public Optional<Tickets> findById(Integer id);

	public List<Tickets> findByStatutLike(String statut);

	public List<Tickets> findByIntervenantIdLike(Integer intervenantid);

	
	public Tickets findTopByOrderByIdDesc();


	public List<Tickets> findByAspirantIdLikeAndStatutLike(Integer intervenantid, String statut);
	
	@Query(value="select * from tickets inner join offre on tickets.id = offre.id_ticket where offre.offre_deja_faite = 1 and id_intervenant = :idIntervenant group by tickets.ID",nativeQuery=true)
	public List<Tickets> findListToDisplayInPool(@Param("idIntervenant")Integer idIntervenant);

	public Tickets findByStatut(String statut);
  
  @Query(value = "SELECT * FROM tickets where tickets.aspirant_id = :idAspirant ORDER BY ID DESC LIMIT 1", nativeQuery = true)
	public Tickets lastCreatedTicket(@Param("idAspirant") Integer idAspirant);

	public List<Tickets> findByLanguageLibraryIn(Set languageLibrary);

	public List<Tickets> findDistinctTop3ByLanguageLibraryInOrderByLikesDesc(Set languageLibrary);
	
}
