package fr.formation.afpa.service;

import java.io.IOException;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.dao.IPictureDao;
import fr.formation.afpa.dao.IUserDao;
import fr.formation.afpa.domain.PictureDb;
import fr.formation.afpa.domain.UserProfile;

@Service
@Transactional
public class PictureService implements IPictureService {

	@Autowired
	private IPictureDao dao;
	@Autowired
	private IUserDao iudao;

	public PictureDb save(UserProfile userProfile, MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		PictureDb checkPdb = null;
		if (dao.findByUserProfileLike(userProfile) != null && file!=null ) {
			System.err.println("------------------findByUserProfileLike-------------------");
			checkPdb = dao.findByUserProfileLike(userProfile);
			checkPdb.setImage(file.getBytes());
			checkPdb.setName(fileName);
			checkPdb.setType(file.getContentType());
			return dao.save(checkPdb);
		} else {
			System.err.println("------------------Else-------------------");
			PictureDb pdb = new PictureDb(fileName, file.getContentType(), file.getBytes(), userProfile);
			return dao.save(pdb);
		}
	}

	public PictureDb getFile(Integer id) {
		return dao.findById(id).get();
	}

	public Stream<PictureDb> getAllFiles() {
		return dao.findAll().stream();
	}

	@Override
	public PictureDb findByUserProfileLike(UserProfile userProfile) {
		return dao.findByUserProfileLike(userProfile);
	}

}
