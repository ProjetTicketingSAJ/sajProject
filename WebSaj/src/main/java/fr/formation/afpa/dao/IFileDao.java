package fr.formation.afpa.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.domain.FileDb;
import fr.formation.afpa.domain.Tickets;

@Repository
public interface IFileDao extends JpaRepository<FileDb, Integer>{

	public FileDb save(MultipartFile file) throws IOException;
	
	public List<FileDb> findByTicketsLike(Tickets ticket);
	
	
}
