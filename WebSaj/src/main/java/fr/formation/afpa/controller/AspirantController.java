package fr.formation.afpa.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.formation.afpa.domain.CodingLanguage;
import fr.formation.afpa.domain.FileDb;
import fr.formation.afpa.domain.Intervention;
import fr.formation.afpa.domain.LanguageLibrary;
import fr.formation.afpa.domain.Offre;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;
import fr.formation.afpa.service.CodingLanguageService;
import fr.formation.afpa.service.FileService;
import fr.formation.afpa.service.InterventionService;
import fr.formation.afpa.service.LanguageLibraryService;
import fr.formation.afpa.service.TicketService;
import fr.formation.afpa.service.UserService;

@Controller
public class AspirantController {

	LanguageLibraryService languageLibraryService;
	TicketService ticketService;
	CodingLanguageService codingLanguageService;
    UserService userService;
	FileService fileService;
	InterventionService interventionService;

	String statutOuvert = "O";

	String statutEnCours = "E";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	
	public AspirantController() {
	}

	@Autowired
	public AspirantController(LanguageLibraryService languageLibraryService, TicketService ticketService,
			CodingLanguageService codingLanguageService, FileService fileService, UserService userService,InterventionService interventionService) {
		this.userService =userService;
		this.languageLibraryService = languageLibraryService;
		this.ticketService = ticketService;
		this.codingLanguageService = codingLanguageService;
		this.fileService = fileService;
		this.interventionService =  interventionService;
	}

	/* Atterrissage sur la page des tickets aspirant */
	@RequestMapping(path = "/ticketsAspirant", method = RequestMethod.GET)
	public String ticketsAspirant(Model m, HttpServletRequest request) {
		System.out.println(
				"++++++++++++++++++++++++++++++++++++++++++ ticketsAspirant +++++++++++++++++++++++++++++++++++++++++++++++");
		HttpSession httpSession = request.getSession();
		Integer id = (Integer) httpSession.getAttribute("aspirantId");

		List<Tickets> listOffresOuverte = ticketService.findByAspirantIdLikeAndStatutLike(id, statutOuvert);
		List<Tickets> listOffresEnCours = ticketService.findByAspirantIdLikeAndStatutLike(id, statutEnCours);
		System.out.println("=============================listTickets OUVERT======================");
		System.out.println(listOffresOuverte);
		System.out.println("=============================listTickets EN COURS ======================");
		System.out.println(listOffresEnCours);
		m.addAttribute("listOffresOuverte", listOffresOuverte);
		m.addAttribute("listOffresEnCours", listOffresEnCours);
		return "MesTicketAspirant";

	}
	
	@RequestMapping(path="/VoirOffres", method = RequestMethod.GET)
	public String VoirOffre(Model model,  HttpServletRequest request, @RequestParam Integer idTicket,  Principal principal ) {
	
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = loginedUser.getUsername();
         
        System.err.println("*******************> " + userInfo + " <*******************");
        UserProfile user = userService.findByLogin(userInfo);
        System.err.println("====================> " + user + " <====================");
        Integer id = user.getId();
        System.err.println("====================> " + id + " <====================");

		/* trouver ticket par son ID  */
		Tickets ticket = ticketService.findById(idTicket).orElse(null);
		System.err.println(ticket);
		
		/* Ajouter ticket à liste d'offres */
		Set<Offre> offresTickets = ticket.getOffres();

		
		System.err.println(offresTickets);
		
				
		model.addAttribute("offresTickets", offresTickets);
		
		return "mesOffres";
	}
	
	/* Accepter l'offre */
	@RequestMapping(path = "/accepterOffre", method = RequestMethod.POST)
	public String AccepterOffre(Model m, HttpServletRequest request, @RequestParam String idIntervenant,
			@RequestParam String idTicket) {
		System.err.println("JE RENTRE ICI");
		
		//Récupération du ticket et recherche du ticket
		Integer id = Integer.parseInt(idTicket);	
		Tickets ticket = ticketService.findById(id).orElse(null);
		
		//Modification du statut du ticket à "en cours"
		ticket.setStatut(statutEnCours);
		
		//modification de l'id de l'intervenant validé sur le ticket 
		Integer idInterv = Integer.parseInt(idIntervenant);
		ticket.setIntervenantId(idInterv);
		
		//Création de l'intervention sur le ticket
		LocalDateTime now = LocalDateTime.now();
		Date date = convertToDateViaSqlTimestamp(now);
		UserProfile user = userService.findById(idInterv).orElse(null);
		Intervention intervention = new Intervention();
		intervention.setDateDebutIntervention(date);
		intervention.setTickets(ticket);
		intervention.setUsers(user);
		interventionService.save(intervention);
		ticketService.save(ticket);
		System.out.println("============================= Accepter Offre ============================");
		System.out.println(ticket);

		return "redirect:/ticketsAspirant";

	}
	
	/* Atterrissage sur la création du ticket aspirant */
	@RequestMapping(path = "/creationTicket", method = RequestMethod.GET)
	public String creationTicket(Model m) {
		// Liste des coding language
		List<CodingLanguage> languageList = codingLanguageService.findAll();

		System.out.println(languageList);

		m.addAttribute("tickets", new Tickets());
		m.addAttribute("languageList", languageList);
		System.out.println();
		return "creationTicket";

	}

	/* Enregistrement ticket aspirant en bdd */

	@RequestMapping(path = "/createTicket", method = RequestMethod.POST)
	public String createTicket(Model m, HttpServletRequest request, @ModelAttribute Tickets tickets,
			@RequestParam Set<MultipartFile> fileUpload, @RequestParam List<String> tagsinput) {
		HttpSession httpSession = request.getSession();
		LocalDateTime now = LocalDateTime.now();
		Date date = convertToDateViaSqlTimestamp(now);
		Integer id = (Integer) httpSession.getAttribute("aspirantId");

		// Création nouvelle liste pour gérer
		List<String> newList = new ArrayList<String>();

		// Traverse la première liste
		for (String element : tagsinput) {
			// Si l'élément n'est pas présent dans la nvelle liste
			// alors l'ajouter
			if (!newList.contains(element)) {
				newList.add(element);
			}
		}

		Set<LanguageLibrary> set = new HashSet<>();
		/*
		 * Boucle qui ajoute le(s) tag(s) en bdd s'il(s) n'existe(nt) pas et set le
		 * ticket.library || set seulement le ticket.languageLibrary lorsque le tag
		 * existe déjà en bdd
		 */
		for (String l : newList) {
			LanguageLibrary ll = languageLibraryService.findByNom(l);
			if (ll != null && l.equals(ll.getNom())) {

				LanguageLibrary lan = languageLibraryService.findByNom(l);
				set.add(lan);

			} else {
				LanguageLibrary langu = new LanguageLibrary(l);
				languageLibraryService.save(langu);
				LanguageLibrary lan = languageLibraryService.findByNom(l);
				set.add(lan);

				tickets.setLanguageLibrary(set);
			}
		}

		tickets.setDateCreation(date);

		tickets.setStatut(statutOuvert);
		tickets.setLanguageLibrary(set);
		tickets.setAspirantId(id);

		System.out.println(tickets.getLanguageLibrary());
		System.out.println(tickets);

		// enregistrement du ticket
		ticketService.save(tickets);

		// enregistrement des fichiers joints
		try {
			if (fileUpload != null)
				for (MultipartFile multi : fileUpload) {
					fileService.save(tickets, multi);
				}

		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/creationTicket";
		}

		return "redirect:/ticketsAspirant";
	}


	/* Visualisation d'un ticket spécifique après avoir cliqué dessus */
	@RequestMapping(path = "/monTicket", method = RequestMethod.GET)
	public String monTicket(Model m, @RequestParam String idTicket) {
		System.out.println(idTicket);
		Integer id = Integer.parseInt(idTicket);
		//Recherche du ticket à afficher
		Optional<Tickets> ticket = ticketService.findById(id);

		//ajout des tags du tickets à une liste de noms pour les afficher
		List<String> listLibrary = new ArrayList<String>();
		for (LanguageLibrary l : ticket.get().getLanguageLibrary()) {
			listLibrary.add(l.getNom());
			System.out.println(l.getNom());
		}
		System.out.println(listLibrary);
		ticket.ifPresent(tick -> m.addAttribute("ticket", tick));

		
		List <FileDb> files = new ArrayList<>();
		for(FileDb f : ticket.get().getFile()) {
	
			files.add(f);
		}

		m.addAttribute("files", files);
		m.addAttribute("listLibrary", listLibrary);
		System.out.println(ticket);

		return "TicketAspirant";

	}

	/* Atterrissage sur la page des offres pour l'aspirant */
	@RequestMapping(path = "/offre", method = RequestMethod.GET)
	public String offre(Model m) {
		List<Tickets> listTickets = ticketService.findAll();

		m.addAttribute("listTickets", listTickets);

		return "Offre";

	}

	public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
		return java.sql.Timestamp.valueOf(dateToConvert);
	}

	@RequestMapping(path = "/profilIntervenant")
	public String profil() {
		return "profilIntervenant";
	}

}

