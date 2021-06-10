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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

/**
 * Tickets generated by hbm2java
 */
@Entity
@Table(name = "tickets", catalog = "SAJ")
public class Tickets implements java.io.Serializable {

	private Integer id;
	@NotEmpty(message = "Titre ne peut être vide")
	private String titre;
	@NotEmpty(message = "Veuillez ajouter une problématique")
	private String expoProblematique;
	private String exceptionConsole;
	private Date dateCreation;
	private Date dateCloture;
	private String commentInterv;
	private String commentAppren;
	private String statut;
	private Set<UserProfile> users = new HashSet<UserProfile>(0);

	private Set<LanguageLibrary> languageLibrary = new HashSet<LanguageLibrary>(0);
	private Integer aspirantId;
	private Integer intervenantId;
	private Set<Offre> offres = new HashSet<Offre>(0);
	private Set<Intervention> intervention = new HashSet<Intervention>(0);
	private Integer codingLanguage;

	private String solutionTicket;
	private Integer nbOffres;
	private Integer likes;
	private Set<FileDb> file = new HashSet<FileDb>(0);

	public Tickets() {
	}

	public Tickets(Integer id, LanguageLibrary languageLibrary) {
		this.id = id;

	}

	public Tickets(Integer id, String titre, String expoProblematique, String exceptionConsole, Date dateCreation,
			Date dateCloture, String commentInterv, String commentAppren, String statut, Set<UserProfile> users,
			Set<LanguageLibrary> languageLibrary, Integer aspirantId, Integer intervenantId, Set<Offre> offres,
			Integer codingLanguage, Set<FileDb> file, Set<Intervention> intervention, String solutionTicket,
			Integer nbOffres, Integer likes) {

		this.id = id;
		this.titre = titre;
		this.expoProblematique = expoProblematique;
		this.exceptionConsole = exceptionConsole;
		this.dateCreation = dateCreation;
		this.dateCloture = dateCloture;
		this.commentInterv = commentInterv;
		this.commentAppren = commentAppren;
		this.statut = statut;
		this.users = users;
		this.languageLibrary = languageLibrary;
		this.aspirantId = aspirantId;
		this.intervenantId = intervenantId;
		this.offres = offres;
		this.codingLanguage = codingLanguage;
		this.file = file;
		this.intervention = intervention;
		this.solutionTicket = solutionTicket;
		this.nbOffres = nbOffres;
		this.likes = likes;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	@JoinColumn(name = "ID")
//	public Intervention getIntervention() {
//		return this.intervention;
//	}
//
//	public void setIntervention(Set<Intervention> intervention) {
//		this.intervention = intervention;
//	}

	@Column(name = "Titre")
	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	@Column(name = "ExpoProblematique")
	public String getExpoProblematique() {
		return this.expoProblematique;
	}

	public void setExpoProblematique(String expoProblematique) {
		this.expoProblematique = expoProblematique;
	}

	@Column(name = "exception_console")
	public String getExceptionConsole() {
		return this.exceptionConsole;
	}

	public void setExceptionConsole(String exceptionConsole) {
		this.exceptionConsole = exceptionConsole;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DateCreation", length = 10)
	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DateCloture", length = 10)
	public Date getDateCloture() {
		return this.dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	@Column(name = "CommentInterv")
	public String getCommentInterv() {
		return this.commentInterv;
	}

	public void setCommentInterv(String commentInterv) {
		this.commentInterv = commentInterv;
	}

	@Column(name = "CommentAppren")
	public String getCommentAppren() {
		return this.commentAppren;
	}

	public void setCommentAppren(String commentAppren) {
		this.commentAppren = commentAppren;
	}

	@Column(name = "Statut")
	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tickets_user", catalog = "SAJ", joinColumns = {
			@JoinColumn(name = "Tickets_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Intervenant_id", nullable = false, updatable = false) })
	public Set<UserProfile> getUsers() {
		return this.users;
	}

	public void setUsers(Set<UserProfile> users) {
		this.users = users;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ticket_has_tag", catalog = "SAJ", joinColumns = {
			@JoinColumn(name = "id_ticket", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_tag", nullable = false, updatable = false) })
	public Set<LanguageLibrary> getLanguageLibrary() {
		return languageLibrary;
	}

	public void setLanguageLibrary(Set<LanguageLibrary> languageLibrary) {
		this.languageLibrary = languageLibrary;
	}

	@Override
	public String toString() {
		return "Tickets [id=" + id + ", titre=" + titre + ", expoProblematique=" + expoProblematique
				+ ", exceptionConsole=" + exceptionConsole + ", dateCreation=" + dateCreation + ", dateCloture="
				+ dateCloture + ", commentInterv=" + commentInterv + ", commentAppren=" + commentAppren + ", statut="
				+ statut + ", languageLibrary=" + languageLibrary + ", aspirantId=" + aspirantId + ", intervenantId="
				+ intervenantId + "]";
	}

	@Column(name = "aspirant_id")
	public Integer getAspirantId() {
		return aspirantId;
	}

	public void setAspirantId(Integer aspirantId) {
		this.aspirantId = aspirantId;
	}

	@Column(name = "intervenant_id")
	public Integer getIntervenantId() {
		return intervenantId;
	}

	public void setIntervenantId(Integer intervenantId) {
		this.intervenantId = intervenantId;
	}

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Offre.class, mappedBy = "tickets")
	public Set<Offre> getOffres() {
		return offres;
	}

	public void setOffres(Set<Offre> offres) {
		this.offres = offres;
	}

	@Column(name = "id_coding_language")
	public Integer getCodingLanguage() {
		return codingLanguage;
	}

	public void setCodingLanguage(Integer codingLanguage) {
		this.codingLanguage = codingLanguage;
	}

	@OneToMany(fetch = FetchType.LAZY, targetEntity = FileDb.class, mappedBy = "tickets", cascade = { CascadeType.ALL })
	public Set<FileDb> getFile() {
		return file;
	}

	public void setFile(Set<FileDb> file) {
		this.file = file;
	}

	@OneToMany(fetch = FetchType.LAZY, targetEntity = Intervention.class, mappedBy = "tickets", cascade = {
			CascadeType.ALL })
	public Set<Intervention> getIntervention() {
		return intervention;
	}

	public void setIntervention(Set<Intervention> intervention) {
		this.intervention = intervention;
	}

	@Column(name = "solution_ticket")
	public String getSolutionTicket() {
		return solutionTicket;
	}

	public void setSolutionTicket(String solutionTicket) {
		this.solutionTicket = solutionTicket;
	}

	@Column(name = "nb_offres")
	public Integer getNbOffres() {
		return nbOffres;
	}

	public void setNbOffres(Integer nbOffres) {
		this.nbOffres = nbOffres;
	}

	@Column(name = "nb_likes")
	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

}
