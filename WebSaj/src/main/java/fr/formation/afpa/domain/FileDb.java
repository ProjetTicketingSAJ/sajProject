package fr.formation.afpa.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "file", catalog = "SAJ")
public class FileDb{
	

	private Integer id;
	
	private String name;
	private String type;
	private byte[] fichier;
	
	private Tickets tickets;
	private UserProfile user;
	
	public FileDb() {
	}
	
	public FileDb(String name, String type, byte[] fichier,Tickets tickets,UserProfile user) {
		this.name = name;
		this.type = type;
		this.fichier = fichier;
		this.tickets = tickets;
		this.user = user;
	}

	  public FileDb(String name, String type, byte[] fichier) {
	    this.name = name;
	    this.type = type;
	    this.fichier = fichier;
	  }
	
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_file", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Lob
	@Column(name = "fichier", nullable = false)
	public byte[] getFichier() {
		return fichier;
	}

	public void setFichier(byte[] fichier) {
		this.fichier = fichier;
	}

	@ManyToOne
	@JoinColumn(name ="id_ticket")
	public Tickets getTickets() {
		return tickets;
	}

	public void setTickets(Tickets tickets) {
		this.tickets = tickets;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "FileDb [id=" + id + ", name=" + name + ", type=" + type + ", fichier=" + Arrays.toString(fichier)
				+ ", tickets=" + tickets + "]";
	}
	
	
	
}
