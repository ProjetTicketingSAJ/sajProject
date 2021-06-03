
package fr.formation.afpa.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.formation.afpa.domain.FileDb;
import fr.formation.afpa.domain.Intervention;
import fr.formation.afpa.domain.LanguageLibrary;
import fr.formation.afpa.domain.Offre;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;
import fr.formation.afpa.service.FileService;
import fr.formation.afpa.service.InterventionService;
import fr.formation.afpa.service.LanguageLibraryService;
import fr.formation.afpa.service.OffreService;
import fr.formation.afpa.service.TicketService;
import fr.formation.afpa.service.UserService;

@Controller
public class IntervenantController {
	LanguageLibraryService languageLibraryService;
	TicketService ticketService;
	OffreService offreService;
	UserService userService;
	FileService fileService;
	InterventionService interventionService;
	String statut = "O";

	public IntervenantController() {
	}

	@Autowired

	public IntervenantController(LanguageLibraryService languageLibraryService, TicketService ticketService,
			OffreService offreService, UserService userService, FileService fileService,
			InterventionService interventionService) {

		this.languageLibraryService = languageLibraryService;
		this.ticketService = ticketService;
		this.offreService = offreService;
		this.userService = userService;
		this.fileService = fileService;
		this.interventionService = interventionService;
	}

	/* Atterrissage sur la page zone tickets intervenant */
	@RequestMapping(path = "/zoneTickets", method = RequestMethod.GET)
	public String zoneTickets(Model m) {

		List<Tickets> listTickets = ticketService.findByStatutLike(statut);

		m.addAttribute("listTickets", listTickets);

		return "ZoneTickets";

	}

	/* Affichage de mes tickets Intervenant grace a la session */
	@RequestMapping(path = "/ticketsInervenant", method = RequestMethod.GET)
	public String ticketsInervenant(Model m, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Integer interId = (Integer) httpSession.getAttribute("aspirantId");
		UserProfile user = userService.findById(interId).orElse(null);
		System.out.println(
				"===================+++++++++++IntervenantId+++++++++++++++++++++++++++++++++++++++" + interId);
		List<Tickets> listTickets = ticketService.findByIntervenantIdLike(interId);
		m.addAttribute("user", user);
		m.addAttribute("listTickets", listTickets);

		return "MesTickets";

	}

	/* Visualisation d'un ticket spécifique après avoir cliqué dessus */
	@RequestMapping(path = "/monTicketintervenant", method = RequestMethod.POST)
	public String monTicket(Model m, @RequestParam String idTicket) {
		System.out.println(idTicket);
		Integer id = Integer.parseInt(idTicket);
		Optional<Tickets> ticket = ticketService.findById(id);

		// Récupération des Tags du ticket
		Set<LanguageLibrary> libraryTicket = ticket.get().getLanguageLibrary();

		List<String> listLibrary = new ArrayList<String>();
		// Ajout des noms des tags du ticket à une liste de noms
		for (LanguageLibrary l : libraryTicket) {
			listLibrary.add(l.getNom());
			System.out.println(l.getNom());
		}

		ticket.ifPresent(tick -> m.addAttribute("ticket", tick));

		// Recherche et envoi des fichiers liés qu ticket
		Tickets tickets = ticketService.findById(ticket.get().getId()).orElse(null);
		List<FileDb> files = new ArrayList<>();
		for (FileDb f : tickets.getFile()) {

			files.add(f);
		}

		m.addAttribute("listLibrary", listLibrary);
		m.addAttribute("files", files);

		return "monTicketIntervenant";

	}

	/*
	 * Proposition d'une soluce
	 */
	@RequestMapping(path = "/envoiSoluce", method = RequestMethod.POST)
	public String envoiSoluce(Model m, @RequestParam String solution, Principal principal,
			@RequestParam String idTicket) {
		System.out.println(idTicket);
		Integer id = Integer.parseInt(idTicket);
		Optional<Tickets> ticket = ticketService.findById(id);
		Tickets ticketSoluce = ticketService.findById(id).orElse(null);
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = loginedUser.getUsername();
		UserProfile user = userService.findByLogin(userInfo);

		Intervention intervention = new Intervention();
		intervention = interventionService.findByTicketsAndUsers(ticketSoluce, user);
		System.out.println("*******************intervention******************* : " + ticketSoluce);
		System.out.println("*******************intervention******************* : " + user);
		System.out.println("*******************intervention******************* : " + intervention);

		intervention.setSolution(solution);

		interventionService.save(intervention);
		// envoi des données pour la page d'après

		// Récupération des Tags du ticket
		Set<LanguageLibrary> libraryTicket = ticket.get().getLanguageLibrary();

		List<String> listLibrary = new ArrayList<String>();
		// Ajout des noms des tags du ticket à une liste de noms
		for (LanguageLibrary l : libraryTicket) {
			listLibrary.add(l.getNom());
			System.out.println(l.getNom());
		}

		ticket.ifPresent(tick -> m.addAttribute("ticket", tick));

		// Recherche et envoi des fichiers liés qu ticket
		Tickets tickets = ticketService.findById(ticket.get().getId()).orElse(null);
		List<FileDb> files = new ArrayList<>();
		for (FileDb f : tickets.getFile()) {

			files.add(f);
		}

		m.addAttribute("listLibrary", listLibrary);
		m.addAttribute("files", files);

		return "monTicketIntervenant";

	}

	/* Proposition offre */
	@RequestMapping(path = "/envoiOffre", method = RequestMethod.POST)
	public String envoiOffre(HttpServletRequest request, @RequestParam String idTick, Double montant, Date dateLimite) {

		// Date création ticket
		HttpSession httpSession = request.getSession();
		LocalDateTime now = LocalDateTime.now();
		Date date = convertToDateViaSqlTimestamp(now);
		// Recherche du ticket par id
		Integer id = Integer.parseInt(idTick);
		Optional<Tickets> ticket = ticketService.findById(id);

		System.out.println("Le ticket : " + ticket);

		// Recherche de l'intervenant qui se positionne

		Integer idIntervenant = (Integer) httpSession.getAttribute("aspirantId");
		Optional<UserProfile> intervenant = userService.findById(idIntervenant);

		// Création de l'offre

		System.out.println("Le ticket : " + ticket);
		// Création de l'offre

		Offre offre = new Offre();
		offre.setDateCreation(date);
		offre.setTickets(ticket.get());
		offre.setDateLimiteSoluce(dateLimite);
		offre.setMontant(montant);
		offre.setIntervenant(intervenant.get());
		System.out.println(offre);

		offreService.save(offre);

		return "redirect:/zoneTickets";

	}

	public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
		return java.sql.Timestamp.valueOf(dateToConvert);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/* Profil Intervenant */
	@RequestMapping(path = "profilIntervenant", method = RequestMethod.GET)
	public String profilIntervenant(Model model, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Integer idIntervenant = (Integer) httpSession.getAttribute("aspirantId");
		System.err.println(idIntervenant);

		UserProfile user = userService.findById(idIntervenant).orElse(null);
		System.err.println(user.getCodingLanguage());
		model.addAttribute("userLang", user.getCodingLanguage());
		model.addAttribute("user", user);
		return "profilIntervenant";
	}

  /*Lecture et téléchargement du fichier*/
  @GetMapping("/file/{id}")
  public void downloadFile(@PathVariable Integer id, HttpServletResponse resp) throws IOException {

      FileDb dbFile = fileService.getFile(id);
 
      byte[] byteArray =  dbFile.getFichier(); // read the byteArray

      resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType()); 
      resp.setHeader("Content-Disposition", "attachment; filename=" + dbFile.getName());
      resp.setContentLength(byteArray.length);

      OutputStream os = resp.getOutputStream();
      try {
          os.write(byteArray, 0, byteArray.length);
      } finally {
          os.close();
      } 

  }
  
  @RequestMapping("/chatRoom")
  public String chatRoom() {
	  return "chatRoom";
  }
}


	


