package fr.formation.afpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.formation.afpa.domain.AppRole;

public interface IRoleDao extends JpaRepository<AppRole, Integer> {
}