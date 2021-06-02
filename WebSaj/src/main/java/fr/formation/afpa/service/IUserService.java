package fr.formation.afpa.service;

import java.util.Optional;

import fr.formation.afpa.domain.UserProfile;

public interface IUserService {
	
	
	public UserProfile findByLoginAndPassword(String log, String mdp) ;

	public Optional<UserProfile> findById(Integer id);
	
	public void save(UserProfile user);
	
	public UserProfile findTopByOrderByIdDesc();
	
	public UserProfile findByLogin(String login);

}
