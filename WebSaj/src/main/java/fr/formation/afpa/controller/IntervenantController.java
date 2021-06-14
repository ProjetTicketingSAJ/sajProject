
package fr.formation.afpa.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
import fr.formation.afpa.service.OffreService;
import fr.formation.afpa.service.PictureService;
import fr.formation.afpa.service.TicketService;
import fr.formation.afpa.service.UserService;

@Controller
public class IntervenantController {
	LanguageLibraryService languageLibraryService;
	static TicketService ticketService;
	static OffreService offreService;
	static UserService userService;
	FileService fileService;
	InterventionService interventionService;
	PictureService pservice;
	CodingLanguageService codingLanguageService;
	String statut = "O";
	String statutEncours = "E";
	String statutFerme = "F";
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public IntervenantController() {
	}

	@Autowired

	public IntervenantController(LanguageLibraryService languageLibraryService, TicketService ticketService,
			OffreService offreService, UserService userService, FileService fileService,
			InterventionService interventionService, PictureService pservice,
			CodingLanguageService codingLanguageService) {

		this.languageLibraryService = languageLibraryService;
		this.ticketService = ticketService;
		this.offreService = offreService;
		this.userService = userService;
		this.fileService = fileService;
		this.interventionService = interventionService;
		this.pservice = pservice;
		this.codingLanguageService = codingLanguageService;
	}

	/* Atterrissage sur la page zone tickets intervenant */
	@RequestMapping(path = "/zoneTickets", method = RequestMethod.GET)
	public String zoneTickets(Model m, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = loginedUser.getUsername();
		UserProfile user = userService.findByLogin(userInfo);
		List<Tickets> listTickets = ticketService.findListToDisplayInPool(user.getId());

		// Tickets ouverts
		List<Tickets> listTicketsOuverts = ticketService.findByStatutLike(statut);
		// suppression des tickets sur lesquels l'intervenant est déjà positionné de la
		// liste globale
		listTicketsOuverts.removeAll(listTickets);

		// trouver les offres encore ouvertes sur lesquelles l'intervenant est
		// positionné
		List<Offre> offres = offreService.findTicketsOuvertsEtNonPerimes(user.getId());
		// Ajouter à une liste de tickets, toutes les offres qui ne sont pas encore
		// périmées
		List<Tickets> tickets = new ArrayList<>();
		for (Offre o : offres) {
			try {
				// renvoie false lorsque la date du jour est supérieure à la date de péremption
				// pour chaque offre
				// le ticket n'est donc pas encore périmé
				if (compareDateOffer(o.getDatePeremption()) == false) {
					tickets.add(o.getTickets());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
//		
		m.addAttribute("listTicketsAModifier", tickets);

		m.addAttribute("listTickets", listTicketsOuverts);

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
		List<Tickets> listTickets = ticketService.findByIntervenantIdLikeAndStatutLike(interId, statutEncours);
		m.addAttribute("user", user);
		m.addAttribute("listTickets", listTickets);

		return "MesTickets";

	}

	/* Visualisation d'un ticket spécifique après avoir cliqué dessus */
	@RequestMapping(path = "/monTicketintervenantAvecProposition", method = RequestMethod.POST)
	public String monTicket(Model m, HttpServletRequest request, @RequestParam String idTicket) {
		HttpSession httpSession = request.getSession();
		// geting profile for chatRoom
		UserProfile user = userService.findById((Integer) httpSession.getAttribute("aspirantId")).get();
		String userLogin = user.getLogin();

		System.out.println(idTicket);
		Integer id = Integer.parseInt(idTicket);
		Optional<Tickets> ticket = ticketService.findById(id);
		String solution = new String();

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
		List<FileDb> filesIntervenant = new ArrayList<>();

		for (FileDb f : tickets.getFile()) {
			UserProfile userProfile = f.getUser();
			if (f.getFichier().length > 0 && userProfile.getTitle().equals("A")) {
				files.add(f);
			} else if (f.getFichier().length > 0 && userProfile.getTitle().equals("I")) {
				filesIntervenant.add(f);
			}
		}

		m.addAttribute("listLibrary", listLibrary);
		m.addAttribute("files", files);
		m.addAttribute("filesIntervenant", filesIntervenant);
		m.addAttribute("solution", solution);
		// attrib for chat
		m.addAttribute("userLogin", userLogin);
		m.addAttribute("ticketId", id);
		return "InspectionMesTickets";

	}

	/* Visualisation d'un ticket spécifique après avoir cliqué dessus */
	@RequestMapping(path = "/monTicketintervenant", method = RequestMethod.POST)
	public String monTicketInter(Model m, @RequestParam String idTicket) {
		System.out.println(idTicket);
		Integer id = Integer.parseInt(idTicket);
		Optional<Tickets> ticket = ticketService.findById(id);
		String solution = new String();

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
		List<FileDb> filesIntervenant = new ArrayList<>();

		for (FileDb f : tickets.getFile()) {
			UserProfile userProfile = f.getUser();
			if (f.getFichier().length > 0 && userProfile.getTitle().equals("A")) {
				files.add(f);
			} else if (f.getFichier().length > 0 && userProfile.getTitle().equals("I")) {
				filesIntervenant.add(f);
			}
		}

		m.addAttribute("listLibrary", listLibrary);
		m.addAttribute("files", files);
		m.addAttribute("filesIntervenant", filesIntervenant);
		m.addAttribute("solution", solution);
		return "monTicketIntervenant";

	}

	/*
	 * Proposition d'une soluce
	 */
	@RequestMapping(path = "/envoiSoluce", method = RequestMethod.POST)
	public String envoiSoluce(Model m, @ModelAttribute("solution") String solution, BindingResult result,
			Principal principal, @RequestParam Set<MultipartFile> fileUpload, @RequestParam String idTicket) {
		// validation
		if (result.hasErrors()) {
			System.err.println("BINDING RESULT ERROR" + result);
			return "creationTicket";
		}
		System.err.println("NO BINDING RESULT ERROR");

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
		// Indiquer que la solution a été envoyée
		intervention.setSolutionRecue(true);
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

		// Recherche et envoi des fichiers liés au ticket
		Tickets tickets = ticketService.findById(ticket.get().getId()).orElse(null);
		List<FileDb> files = new ArrayList<>();
		List<FileDb> filesIntervenant = new ArrayList<>();
		for (FileDb f : tickets.getFile()) {
			UserProfile userProfile = f.getUser();
			if (f.getFichier().length > 0 && userProfile.getTitle().equals("A")) {
				files.add(f);
			} else if (f.getFichier().length > 0 && userProfile.getTitle().equals("I")) {
				filesIntervenant.add(f);
			}
		}
		// enregistrement des fichiers joints
		try {
			if (fileUpload != null)
				for (MultipartFile multi : fileUpload) {
					fileService.save(tickets, multi, user);
				}

		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/ticketsInervenant";
		}

		m.addAttribute("listLibrary", listLibrary);
		m.addAttribute("files", files);
		m.addAttribute("filesIntervenant", filesIntervenant);

		return "redirect:/ticketsInervenant";

	}

	/* Proposition offre */
	@RequestMapping(path = "/envoiOffre", method = RequestMethod.POST)
	public String envoiOffre(HttpServletRequest request, @RequestParam String idTick, Double montant, Date dateLimite) {

		// Date création ticket
		HttpSession httpSession = request.getSession();
		LocalDateTime now = LocalDateTime.now();

		Date date = convertToDateViaSqlTimestamp(now);
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		// manipulate date
		c.add(Calendar.DATE, 2);

		// convert calendar to date
		Date currentDatePlusTwo = c.getTime();
		System.err.println("CECI EST LA DATE: " + currentDatePlusTwo);
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
		offre.setDatePeremption(currentDatePlusTwo);
		offre.setTickets(ticket.get());
		offre.setDateLimiteSoluce(dateLimite);
		offre.setMontant(montant);
		offre.setIntervenant(intervenant.get());
		offre.setOffreDejaFaite(true);
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
	public String profilIntervenant(ModelAndView mav, Model model, HttpServletRequest request)
			throws UnsupportedEncodingException {
		HttpSession httpSession = request.getSession();
		Integer idIntervenant = (Integer) httpSession.getAttribute("aspirantId");

		UserProfile user = userService.findById(idIntervenant).orElse(null);

		model.addAttribute("userLangFindAll", codingLanguageService.findAll());
		model.addAttribute("userLang", user.getCodingLanguage());
		model.addAttribute("user", user);
		return "profilIntervenant";
	}

	/*
	 * Update du profil info
	 */
	@RequestMapping(path = "/upDate", method = RequestMethod.POST)
	public String upDate(Model m, @ModelAttribute("user") UserProfile userProfile, Principal principal, Model model,
			HttpServletRequest request, @RequestParam Set<MultipartFile> fileUpload) {

		System.err.println("===============================profilIntervenant========================================");

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = loginedUser.getUsername();
		UserProfile user = userService.findByLogin(userInfo);
		List<CodingLanguage> langList = codingLanguageService.findAll();

		user.setPrenom(userProfile.getPrenom());
		user.setNom(userProfile.getNom());
		user.setEmail(userProfile.getEmail());
		user.setTelephone(userProfile.getTelephone());
		user.setDateNaiss(userProfile.getDateNaiss());

		try {
			if (fileUpload != null && !fileUpload.isEmpty()) {

				for (MultipartFile multi : fileUpload) {

					if (multi.getBytes().length > 0) {
						System.err.println("--------fileUpload-------- : " + multi.getBytes().length);
						System.err.println("--------fileUpload-------- : " + fileUpload.isEmpty());
						pservice.save(user, multi);
					}
				}

			} else {
				System.err.println("ELSE MOFOOOOOOOOOOOOO");

			}

		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:/";
		}

		model.addAttribute("langList", langList);
		userService.save(user);

		return "redirect:/profilIntervenant";
	}

	/*
	 * Update du profil info
	 */
	@RequestMapping(path = "/upDateLanguage", method = RequestMethod.POST)
	public String upDateLanguage(Model m, Principal principal, Model model, HttpServletRequest request,
			@RequestParam(value = "idChecked", required = false) Set<CodingLanguage> listLang) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = loginedUser.getUsername();
		UserProfile user = userService.findByLogin(userInfo);
		List<CodingLanguage> langList = codingLanguageService.findAll();
		System.err.println("--++--++--++--++--++--++--++--++--++--++--++ : " + langList);
		user.setCodingLanguage(listLang);

		userService.save(user);

		return "redirect:/profilIntervenant";
	}

	@RequestMapping(path = "/upDateCv", method = RequestMethod.POST)
	public String upDateCv(Model m, Principal principal, Model model, HttpServletRequest request,
			@RequestParam String cv) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = loginedUser.getUsername();
		UserProfile user = userService.findByLogin(userInfo);

		user.setCv(cv);

		userService.save(user);

		return "redirect:/profilIntervenant";
	}

	/* Lecture et téléchargement du fichier */
	@GetMapping("/file/{id}")
	public void downloadFile(@PathVariable Integer id, HttpServletResponse resp) throws IOException {

		FileDb dbFile = fileService.getFile(id);
		byte[] byteArray = dbFile.getFichier(); // read the byteArray

//		if (byteArray != null && byteArray.length > 0) {

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

	public static Offre findOffer(Integer idTicket, Integer idIntervenant) {
		Tickets ticket = ticketService.findById(idTicket).orElse(null);
		UserProfile intervenant = userService.findById(idIntervenant).orElse(null);

		Offre offre = offreService.findByTicketsAndIntervenant(ticket, intervenant);

		return offre;

	}

	/* Détachement de l'intervenant sur un ticket */
	@RequestMapping(path = "/detachement", method = RequestMethod.POST)
	public String detachement(Model m, HttpServletRequest request, @RequestParam String idTick) {
		HttpSession httpSession = request.getSession();
		Integer idTicket = Integer.parseInt(idTick);
		Integer idUser = (Integer) httpSession.getAttribute("aspirantId");
		Tickets ticket = ticketService.findById(idTicket).get();
		UserProfile intervenant = userService.findById(idUser).get();
		Intervention intervention = interventionService.findByTicketsAndUsers(ticket, intervenant);
		intervention.setDetache(true);
		interventionService.save(intervention);
		ticket.setIntervenantId(null);
		ticket.setStatut(statut);
		ticketService.save(ticket);

		return "redirect:/ticketsInervenant";

	}

	/* Détachement de l'intervenant sur un ticket */
	@RequestMapping(path = "/abandonnerOffre", method = RequestMethod.POST)
	public String abandonnerOffre(Model m, HttpServletRequest request, @RequestParam String idTicket) {
		HttpSession httpSession = request.getSession();
		Integer idTick = Integer.parseInt(idTicket);
		Integer idUser = (Integer) httpSession.getAttribute("aspirantId");
		Tickets ticket = ticketService.findById(idTick).get();
		UserProfile intervenant = userService.findById(idUser).get();
		Offre offre = offreService.findByTicketsAndIntervenant(ticket, intervenant);
		offreService.delete(offre);

		return "redirect:/zoneTickets";

	}

	@RequestMapping(path = "/MesStats", method = RequestMethod.GET)
	public String mesStat(Model m, HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		Integer intervenantId = (Integer) httpSession.getAttribute("aspirantId");
		UserProfile user = userService.findById(intervenantId).get();
		Integer nbInterventions = interventionService.countDistinctByUsers(user);
		Integer nbOffres = offreService.countDistinctByIntervenant(user);
		Integer totalTicketsResolus = ticketService.countDistinctByStatutAndIntervenantId(statutFerme, intervenantId);
		Integer nbDetachements = interventionService.countDistinctByUsersAndDetache(user, true);
		m.addAttribute("nbInterventions", nbInterventions);
		m.addAttribute("nbOffres", nbOffres);
		m.addAttribute("nbDetachements", nbDetachements);
		m.addAttribute("totalTicketsResolus", totalTicketsResolus);

		return "page_mes_statistiques_Intervenant";
	}

	// savoir si une offre est périmée ou non
	public static boolean compareDateOffer(Date datePeremp) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datePeremption = dateFormat.format(datePeremp);
		if (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(datePeremption).before(new Date())) {
			return true;
		} else {

			return false;
		}

	}

}
