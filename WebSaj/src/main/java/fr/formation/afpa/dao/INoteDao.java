package fr.formation.afpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.afpa.domain.Note;

@Repository
public interface INoteDao extends JpaRepository<Note, Integer> {

}
