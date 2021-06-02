package fr.formation.afpa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.afpa.dao.IMessageDao;

@Service
@Transactional
public class MessageService {

	@Autowired
	private IMessageDao dao;

}
