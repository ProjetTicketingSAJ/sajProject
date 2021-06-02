package fr.formation.afpa.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.dao.ITicketsDao;
import fr.formation.afpa.domain.FileDb;
import fr.formation.afpa.domain.Tickets;

@Service
@Transactional
public class TicketService implements ITicketService {

	@Autowired
	private ITicketsDao dao;

	@Override
	public Tickets save(Tickets ticket) {
		Tickets tickets = dao.save(ticket);
		return tickets;
	}

	@Override
	public List<Tickets> findAll() {
		List<Tickets> list = dao.findAll();
		return list;
	}

	@Override
	public Optional<Tickets> findById(Integer id) {
		Optional<Tickets> ticket = dao.findById(id);
		return ticket;
	}

	@Override
	public List<Tickets> findByStatutLike(String statut) {

		return dao.findByStatutLike(statut);
	}

	@Override
	public List<Tickets> findByIntervenantIdLike(Integer intervenantid) {

		return dao.findByIntervenantIdLike(intervenantid);
	}

	@Override

	public Tickets findTopByOrderByIdDesc() {
		return dao.findTopByOrderByIdDesc();
	}



	public List<Tickets> findByAspirantIdLikeAndStatutLike(Integer intervenantid, String statut) {
		return dao.findByAspirantIdLikeAndStatutLike(intervenantid, statut);
	}


}
