package fr.formation.afpa.domain;
// Generated 23 mai 2021 13:43:00 by Hibernate Tools 5.1.10.Final
import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user_Profile", catalog = "SAJ")
public class UserProfile implements java.io.Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private String telephone;
    private Date dateNaiss;
    private String email;
    private String login;
    private String password;
    private Date dateCreation;
    private Date dateDerniereConnexion;
    private String title;
    private String cv;
    private String discriminator;
    private Set<Note> notes = new HashSet<Note>(0);
    private Set<Compte> comptes = new HashSet<Compte>(0);
    private Set<Tickets> tickets = new HashSet<Tickets>(0);
    private Set<Message> messages = new HashSet<Message>(0);
    private Set<Intervention> interventions = new HashSet<Intervention>(0);
    private Set<CodingLanguage> codingLanguage = new HashSet<CodingLanguage>(0);
    private Set<AppRole> roles = new HashSet<AppRole>(0);
    private boolean enabled;
    public UserProfile() {
    }
    
    public UserProfile(String nom, String prenom, String telephone, Date dateNaiss, String email, String login,
            String password, Date dateCreation, Date dateDerniereConnexion, String title, String cv,
            String discriminator, Set<Note> notes, Set<Compte> comptes, Set<Tickets> tickets, Set<Message> messages,
            Set<Intervention> interventions, Set<CodingLanguage> codingLanguage, Set<AppRole> roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.dateNaiss = dateNaiss;
        this.email = email;
        this.login = login;
        this.password = password;
        this.dateCreation = dateCreation;
        this.dateDerniereConnexion = dateDerniereConnexion;
        this.title = title;
        this.cv = cv;
        this.discriminator = discriminator;
        this.notes = notes;
        this.comptes = comptes;
        this.tickets = tickets;
        this.messages = messages;
        this.interventions = interventions;
        this.codingLanguage = codingLanguage;
        this.roles = roles;
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_coding_language", catalog = "SAJ", joinColumns = {
            @JoinColumn(name = "interven_id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "coding_language_id", nullable = false, updatable = false) })
    public Set<CodingLanguage> getCodingLanguage() {
        return codingLanguage;
    }
    public void setCodingLanguage(Set<CodingLanguage> codingLanguage) {
        this.codingLanguage = codingLanguage;
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
    @Column(name = "Nom")
    public String getNom() {
        return this.nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    @Column(name = "Prenom")
    public String getPrenom() {
        return this.prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    @Column(name = "Telephone")
    public String getTelephone() {
        return this.telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "DateNaiss", length = 10)
    public Date getDateNaiss() {
        return this.dateNaiss;
    }
    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }
    @Column(name = "Email")
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "Login")
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    @Column(name = "Password")
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "DateDerniereConnexion", length = 10)
    public Date getDateDerniereConnexion() {
        return this.dateDerniereConnexion;
    }
    public void setDateDerniereConnexion(Date dateDerniereConnexion) {
        this.dateDerniereConnexion = dateDerniereConnexion;
    }
    @Column(name = "Title")
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "Cv")
    public String getCv() {
        return this.cv;
    }
    public void setCv(String cv) {
        this.cv = cv;
    }
    @Column(name = "Discriminator")
    public String getDiscriminator() {
        return this.discriminator;
    }
    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Note> getNotes() {
        return this.notes;
    }
    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Compte> getComptes() {
        return this.comptes;
    }
    public void setComptes(Set<Compte> comptes) {
        this.comptes = comptes;
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tickets_user", catalog = "SAJ", joinColumns = {
            @JoinColumn(name = "Intervenant_id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "Tickets_id", nullable = false, updatable = false) })
    public Set<Tickets> getTicketses() {
        return this.tickets;
    }
    public void setTicketses(Set<Tickets> tickets) {
        this.tickets = tickets;
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "message_has_user", catalog = "SAJ", joinColumns = {
            @JoinColumn(name = "user_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "message_ID", nullable = false, updatable = false) })
    public Set<Message> getMessages() {
        return this.messages;
    }
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Intervention.class, mappedBy = "users")
    public Set<Intervention> getInterventions() {
        return this.interventions;
    }
    public void setInterventions(Set<Intervention> interventions) {
        this.interventions = interventions;
    }
    @Column(name = "Enabled", length = 1, nullable = false)
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    public Set<AppRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }
}