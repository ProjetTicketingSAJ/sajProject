package fr.formation.afpa.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.afpa.domain.UserProfile;

@Repository
public interface IUserDao extends JpaRepository<UserProfile, Integer> {

	public UserProfile findByLoginAndPassword(String log, String mdp);

	public Optional<UserProfile> findById(Integer id);

	@SuppressWarnings("unchecked")
	public UserProfile save(UserProfile user);

	public UserProfile findTopByOrderByIdDesc();

	public UserProfile findByLogin(String login);

}
