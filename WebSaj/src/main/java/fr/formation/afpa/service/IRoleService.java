package fr.formation.afpa.service;

import java.util.Set;

import fr.formation.afpa.domain.AppRole;

public interface IRoleService {
	public Set<AppRole> findByRoleId(Long id);
}
