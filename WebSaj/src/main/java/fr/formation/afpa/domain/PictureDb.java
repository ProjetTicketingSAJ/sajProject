package fr.formation.afpa.domain;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "picture_profile", catalog = "SAJ")
public class PictureDb {

	private Integer id;
	private String name;
	private String type;
	private byte[] image;

	private UserProfile userprofile;

	public PictureDb() {
	}

	public PictureDb(String name, String type, byte[] image, UserProfile userprofile) {
		this.name = name;
		this.type = type;
		this.image = image;
		this.userprofile = userprofile;
	}

	public PictureDb(String name, String type, byte[] image) {
		this.name = name;
		this.type = type;
		this.image = image;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_picture", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "id_user")
	public UserProfile getUserProfile() {
		return userprofile;
	}

	@Lob
	@Column(name = "picture_file")
	public byte[] getImage() {
		return image;
	}

	@Column(name = "picture_name")
	public String getName() {
		return name;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setUserProfile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PictureDb [id=" + id + ", name=" + name + ", type=" + type + ", image=" + Arrays.toString(image)
				+ ", userprofile=" + userprofile + "]";
	}

}
