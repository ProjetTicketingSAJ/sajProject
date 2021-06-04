package fr.formation.afpa.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.afpa.dao.IUserDao;
import fr.formation.afpa.domain.UserProfile;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private IUserDao dao;

	public UserService() {

	}

	@Override
	public UserProfile findByLoginAndPassword(String log, String mdp) {
		UserProfile user = dao.findByLoginAndPassword(log, mdp);
		return user;
	}

	@Override
	public Optional<UserProfile> findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public void save(UserProfile user) {
		dao.save(user);
	}

	@Override
	public UserProfile findTopByOrderByIdDesc() {

		return dao.findTopByOrderByIdDesc();
	}

	public UserProfile findByLogin(String login) {
		return dao.findByLogin(login);
	}

}
