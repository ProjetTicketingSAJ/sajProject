
package fr.formation.afpa.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.formation.afpa.domain.CodingLanguage;
import fr.formation.afpa.domain.Tickets;
import fr.formation.afpa.domain.UserProfile;
import fr.formation.afpa.service.CodingLanguageService;
import fr.formation.afpa.service.LanguageLibraryService;
import fr.formation.afpa.service.TicketService;
import fr.formation.afpa.service.UserService;
import fr.formation.afpa.utils.EncrytedPasswordUtils;
import fr.formation.afpa.utils.WebUtils;

@Controller
public class LoginController {
	CodingLanguage codingLanguage;
	CodingLanguageService codingLanguageService;
	UserService userService;
	LanguageLibraryService languageLibraryService;
	TicketService ticketService;
	String statutOuvert = "O";
	String statutEnCours = "E";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	public LoginController() {
	}

	/*
	 * redirection Creation profil USER avec ajout de list de langages
	 */
	@RequestMapping("/pageInscription")
	public String pageInscription(Model model) {
		List<CodingLanguage> langList = codingLanguageService.findAll();
		System.err.println(langList);
		model.addAttribute("langList", langList);
		model.addAttribute("user", new UserProfile());
		return "pageInscription";
	}

	/*
	 * Creation profil USER
	 */
	@PostMapping(path = "/inscription")
	public String creationUser(@ModelAttribute UserProfile user, Model model,
			@RequestParam(value = "idChecked", required = false) Set<CodingLanguage> listLang) {
		String passw = user.getPassword();
		user.setPassword(EncrytedPasswordUtils.encrytePassword(passw));
		user.setEnabled(true);
		if (user.getTitle().equals("A")) {
			userService.save(user);
		} else {
			user.setCodingLanguage(listLang);
//          User userFake =  userService.findTopByOrderByIdDesc();
//          Integer idFake = userFake.getId();
//          idFake += 1;
//          user.setId(idFake);
			userService.save(user);
		}
		return "index";
	}

	@RequestMapping("/qsm")
	public String qsm() {
		return "qsm";
	}

	@RequestMapping("/notFound")
	public String notFound() {
		return "404Errorpage";
	}

	@Autowired
	public LoginController(UserService userService, LanguageLibraryService languageLibraryService,
			CodingLanguageService codingLanguageService, TicketService ticketService) {
		this.userService = userService;
		this.languageLibraryService = languageLibraryService;
		this.ticketService = ticketService;
		this.codingLanguageService = codingLanguageService;
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String listEmp(Model model) {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model m, Principal principal) {
		return "redirect:/accueil";
	}

	/*
	 * Méthode validation Login qui donne accès à l'accueil
	 */
	@RequestMapping(path = "/accueil", method = RequestMethod.GET)
	public String getAccueil(Model m, HttpServletRequest request, Principal principal) {
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = loginedUser.getUsername();
		System.out.println("++++++++++++-------------+++++++++++++++++++-------------- : " + userInfo);
		UserProfile user = userService.findByLogin(userInfo);
		HttpSession httpSession = request.getSession();
		try {
			if (user.getTitle().equals("I")) {
				System.out.println("ID INTERVENANT: " + user.getId());
				System.out.println("TITRE INTERVENANT: " + user.getTitle());
				List<Tickets> listTickets = ticketService.findByStatutLike(statutOuvert);
				httpSession.setAttribute("title", user.getTitle());
				httpSession.setAttribute("login", user.getLogin());
				httpSession.setAttribute("aspirantId", user.getId());
				m.addAttribute("listTickets", listTickets);
				return "ZoneTickets";
			} else if (user.getTitle().equals("A")) {
				List<Tickets> listTickets = ticketService.findAll();
				httpSession.setAttribute("aspirantId", user.getId());
				httpSession.setAttribute("title", user.getTitle());
				httpSession.setAttribute("login", user.getLogin());
				System.out.println(user.getId());
				Integer id = (Integer) httpSession.getAttribute("aspirantId");
				m.addAttribute("listTickets", listTickets);
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
		} catch (NoResultException nre) {
			System.out.println("je suis nulle");
			httpSession.setAttribute("error", "**Login et/ou mot de passe incorrect(s)**");
			return "index";
		}
		return "index";
	}

	/* Logout method */
	@RequestMapping(path = "/logoutSuccessful", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
		return "index";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			String userInfo = WebUtils.toString(loginedUser);
			model.addAttribute("userInfo", userInfo);
			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);
		}
		return "403Page";
	}

}