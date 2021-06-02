package fr.formation.afpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.afpa.domain.Message;

@Repository
public interface IMessageDao extends JpaRepository<Message, Integer> {

}
