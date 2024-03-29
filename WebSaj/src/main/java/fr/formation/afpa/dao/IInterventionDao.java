package fr.formation.afpa.dao;

import java.util.List;

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
	
	public List <Intervention> findByTicketsAndUsers(UserProfile user,Tickets tickets);
	
	public Integer countDistinctByUsers(UserProfile intervenant); 
	
	public Integer countDistinctByUsersAndDetache(UserProfile intervenant, boolean detache);
}
