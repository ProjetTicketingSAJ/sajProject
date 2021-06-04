package fr.formation.afpa.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

	@Query(value = "SELECT * FROM tickets where tickets.aspirant_id = :idAspirant ORDER BY ID DESC LIMIT 1", nativeQuery = true)
	public Tickets lastCreatedTicket(@Param("idAspirant") Integer idAspirant);

	public List<Tickets> findByLanguageLibraryIn(Set languageLibrary);

	public List<Tickets> findDistinctTop3ByLanguageLibraryInOrderByLikesDesc(Set languageLibrary);
}
