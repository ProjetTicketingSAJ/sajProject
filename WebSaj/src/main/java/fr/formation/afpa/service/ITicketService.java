package fr.formation.afpa.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.query.Param;

import fr.formation.afpa.domain.LanguageLibrary;
import fr.formation.afpa.domain.Tickets;

public interface ITicketService {
	public Tickets save(Tickets ticket);

	public List<Tickets> findAll();

	public Optional<Tickets> findById(Integer id);

	public List<Tickets> findByStatutLike(String statut);

	public List<Tickets> findByIntervenantIdLike(Integer intervenantid);

	public Tickets findTopByOrderByIdDesc();

	public List<Tickets> findByAspirantIdLikeAndStatutLike(Integer intervenantid, String statut);

	public List<Tickets> findListToDisplayInPool(@Param("idIntervenant") Integer idIntervenant);

	public Tickets findByStatut(String statut);

	List<Tickets> findByLanguageLibraryIn(Set<LanguageLibrary> languageLibrary);

	public Tickets lastCreatedTicket(@Param("idAspirant") Integer idAspirant);

	public List<Tickets> findDistinctTop3ByLanguageLibraryInOrderByLikesDesc(Set languageLibrary);

	public List<Tickets> findDistinctTop3ByStatutLikeAndLanguageLibraryInOrderByLikesDesc(String statut,
			Set languageLibrary);

	public List<Tickets> findDistinctByStatutLikeAndLanguageLibraryIn(String statut, Set languageLibrary);

	public List<Tickets> findTicketsToModifierOffer(@Param("idIntervenant") Integer idIntervenant);

	public List<Tickets> findByIntervenantIdLikeAndStatutLike(Integer intervenantid, String statut);

	public Integer countDistinctByStatutAndIntervenantId(String statut, Integer intervenantId);

}
