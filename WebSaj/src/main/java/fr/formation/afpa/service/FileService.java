package fr.formation.afpa.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.dao.IFileDao;
import fr.formation.afpa.domain.FileDb;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;

@Service
@Transactional
public class FileService implements IFileService {

	@Autowired 
	private IFileDao dao;
	
	public FileDb save(Tickets tickets,MultipartFile file,UserProfile user) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    FileDb FileDb = new FileDb(fileName, file.getContentType(), file.getBytes(),tickets,user);
	    
	    return dao.save(FileDb);
	  }

	  public FileDb getFile(Integer id) {
	    return dao.findById(id).get();
	  }
	  
	  public Stream<FileDb> getAllFiles() {
	    return dao.findAll().stream();
	  }

	@Override
	public List<FileDb> findByTicketsLike(Tickets ticket) {
		return dao.findByTicketsLike(ticket);
	}
	
}
