package fr.formation.afpa.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.afpa.domain.AppRole;

public interface IRoleDao extends JpaRepository<AppRole, Integer> {
	
	public Set<AppRole> findByRoleId(Long id);
}