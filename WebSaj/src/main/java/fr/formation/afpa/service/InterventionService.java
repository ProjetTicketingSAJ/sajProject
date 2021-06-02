package fr.formation.afpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.afpa.dao.IInterventionDao;
import fr.formation.afpa.domain.Intervention;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;

@Service
@Transactional
public class InterventionService implements IInterventionService{

	@Autowired
	private IInterventionDao dao;



	@Override
	public Intervention save(Intervention intervention) {
		return dao.save(intervention);
	}



	@Override
	public Intervention findByTicketsAndUsers(Tickets tickets, UserProfile user) {
		return dao.findByTicketsAndUsers(tickets, user);
	}

}
