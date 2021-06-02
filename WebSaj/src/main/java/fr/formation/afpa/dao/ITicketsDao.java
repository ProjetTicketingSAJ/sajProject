package fr.formation.afpa.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	


}
