package fr.formation.afpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.afpa.dao.IOffreDao;
import fr.formation.afpa.domain.Offre;
import fr.formation.afpa.domain.Tickets;

@Service
@Transactional
public class OffreService implements IOffreService {

	@Autowired
	private IOffreDao dao;

	@Override
	public Offre save(Offre offre) {

		return dao.save(offre);
	}

	@Override
	public Integer findNbOffres(Integer idTicket) {
		return dao.findNbOffres(idTicket);
	}

	@Override
	public List<Offre> findByTickets(Tickets ticket) {
		return dao.findByTickets(ticket);
	}

	
	

}
