package fr.formation.afpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.domain.PictureDb;
import fr.formation.afpa.domain.UserProfile;

@Repository
public interface IPictureDao extends JpaRepository<PictureDb, Integer> {

	public PictureDb save(MultipartFile file);

	public PictureDb findByUserProfileLike(UserProfile userProfile);

}
