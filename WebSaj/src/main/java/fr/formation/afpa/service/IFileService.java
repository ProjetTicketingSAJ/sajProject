package fr.formation.afpa.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.domain.FileDb;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;

public interface IFileService {

	public FileDb save(Tickets tickets,MultipartFile file,UserProfile user) throws IOException;
	
	public FileDb getFile(Integer id) ;
	
	public Stream<FileDb> getAllFiles();
	
	public List<FileDb> findByTicketsLike(Tickets ticket);

}
