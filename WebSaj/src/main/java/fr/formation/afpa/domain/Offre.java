package fr.formation.afpa.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "offre", catalog = "SAJ")
public class Offre implements java.io.Serializable{

	private Integer id;
	private Date dateCreation;
	private Date dateLimiteSoluce;
	private Double montant;
	private Tickets tickets;
	private UserProfile intervenant;
	private boolean ticketVu;
	
	public Offre() {
	}
	
	public Offre(Integer id,Date dateCreation,Date dateLimiteSoluce,Double montant,Tickets tickets,UserProfile intervenant,boolean ticketVu) {
		this.id = id;
		this.dateCreation = dateCreation;
		this.dateLimiteSoluce = dateLimiteSoluce;
		this.montant = montant;
		this.tickets = tickets;
		this.intervenant = intervenant;
		this.ticketVu = ticketVu;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_offre", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "date_creation", nullable = false)
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	@Column(name = "date_limite_solution", nullable = false)
	public Date getDateLimiteSoluce() {
		return dateLimiteSoluce;
	}
	
	public void setDateLimiteSoluce(Date dateLimiteSoluce) {
		this.dateLimiteSoluce = dateLimiteSoluce;
	}
	
	@Column(name = "montant", nullable = false)
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	
	@ManyToOne
	@JoinColumn(name ="id_ticket")
	public Tickets getTickets() {
		return tickets;
	}
	public void setTickets(Tickets tickets) {
		this.tickets = tickets;
	}

	@ManyToOne
	@JoinColumn(name ="id_intervenant")
	public UserProfile getIntervenant() {
		return intervenant;
	}

	public void setIntervenant(UserProfile intervenant) {
		this.intervenant = intervenant;
	}

	@Override
	public String toString() {
		return "Offre [id=" + id + ", dateCreation=" + dateCreation + ", dateLimiteSoluce=" + dateLimiteSoluce
				+ ", montant=" + montant + ", tickets=" + tickets + ", intervenant=" + intervenant + "]";
	}

	@Column(name = "ticket_vu")
	public boolean isTicketVu() {
		return ticketVu;
	}

	public void setTicketVu(boolean ticketVu) {
		this.ticketVu = ticketVu;
	}
	
	
	
//	@Override
//	public String toString() {
//		return "Offre [id=" + id + ", dateCreation=" + dateCreation + ", dateLimiteSoluce=" + dateLimiteSoluce
//				+ ", montant=" + montant + ", tickets=" + tickets + "]";
//	}
	
	
}
