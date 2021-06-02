package fr.formation.afpa.domain;
// Generated 23 mai 2021 13:43:00 by Hibernate Tools 5.1.10.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Intervention generated by hbm2java
 */
@Entity
@Table(name = "intervention", catalog = "SAJ")
public class Intervention implements java.io.Serializable {

	private Integer id;
	private Date dateDebutIntervention;
	private Date dateClotureIntervention;
	private Tickets tickets = new Tickets();
	private UserProfile users = new UserProfile();
	private Set<Note> notes = new HashSet<Note>(0);
	private String solution;

	public Intervention() {
	}

	
	public Intervention(Date dateDebutIntervention, Date dateClotureIntervention, 
			Tickets tickets, UserProfile users, Set<Note> notes,String solution) {
		this.dateDebutIntervention = dateDebutIntervention;
		this.dateClotureIntervention = dateClotureIntervention;
	
		this.tickets = tickets;
		this.users = users;
		this.notes = notes;
		this.solution = solution;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DateDebutIntervention", length = 10)
	public Date getDateDebutIntervention() {
		return this.dateDebutIntervention;
	}

	public void setDateDebutIntervention(Date dateDebutIntervention) {
		this.dateDebutIntervention = dateDebutIntervention;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DateClotureIntervention", length = 10)
	public Date getDateClotureIntervention() {
		return this.dateClotureIntervention;
	}

	public void setDateClotureIntervention(Date dateClotureIntervention) {
		this.dateClotureIntervention = dateClotureIntervention;
	}

	

	@ManyToOne
    @JoinColumn(name = "idticket")
	public Tickets getTickets() {
		return this.tickets;
	}

	public void setTickets(Tickets tickets) {
		this.tickets = tickets;
	}

	@ManyToOne
    @JoinColumn(name = "iduser")
	public UserProfile getUsers() {
		return this.users;
	}

	public void setUsers(UserProfile users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "intervention")
	public Set<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	@JoinColumn(name = "solution")
	public String getSolution() {
		return solution;
	}


	public void setSolution(String solution) {
		this.solution = solution;
	}

	
}