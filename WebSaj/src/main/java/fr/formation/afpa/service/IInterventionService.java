package fr.formation.afpa.service;

import java.util.List;

import fr.formation.afpa.domain.Intervention;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;

public interface IInterventionService {

	public Intervention findByTicketsAndUsers(Tickets tickets,UserProfile user);
	
	public Intervention save(Intervention intervention);
	
	public List <Intervention> findByTicketsAndUsers(UserProfile user,Tickets tickets);

}
