package fr.formation.afpa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.afpa.dao.IOffreDao;
import fr.formation.afpa.domain.Offre;

@Service
@Transactional
public class OffreService implements IOffreService {

	@Autowired
	private IOffreDao dao;

	@Override
	public Offre save(Offre offre) {

		return dao.save(offre);
	}

}
