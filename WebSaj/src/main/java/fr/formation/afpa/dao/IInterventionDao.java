package fr.formation.afpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.afpa.domain.Intervention;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;

@Repository
public interface IInterventionDao extends JpaRepository<Intervention, Integer> {

	public Intervention findByTicketsAndUsers(Tickets tickets, UserProfile user);

	@SuppressWarnings("unchecked")
	public Intervention save(Intervention intervention);
}
