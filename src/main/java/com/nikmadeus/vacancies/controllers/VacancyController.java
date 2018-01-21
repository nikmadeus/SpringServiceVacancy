package com.nikmadeus.vacancies.controllers;

import com.nikmadeus.vacancies.entities.Comment;
import com.nikmadeus.vacancies.entities.User;
import com.nikmadeus.vacancies.entities.Vacancy;
import com.nikmadeus.vacancies.services.CommentService;
import com.nikmadeus.vacancies.services.UserService;
import com.nikmadeus.vacancies.services.VacancyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class VacancyController {
	
	private UserService userService;
	private VacancyService vacancyService;
	private CommentService commentService;
	
	public VacancyController(
			UserService userService, VacancyService vacancyService, CommentService commentService) {
		this.userService = userService;
		this.vacancyService = vacancyService;
		this.commentService = commentService;
	}
	
	@GetMapping("/")
	public String loginReg(
            @Valid @ModelAttribute("user") User user, Model model,
            @RequestParam(value="error", required=false) String error,
            @RequestParam(value="logout", required=false) String logout) {
		if (error != null) {
			model.addAttribute("errorMessage", "Неверные данные! Проверьте данные и повторите попытку ещё раз!");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Вы вышли из учетной записи!");
		}

		return "authorization.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "authorization.jsp";
		} else {
			userService.createUser(user);
			return "redirect:/";
		}
	}
	
	@GetMapping("/vacancies")
	public String allVacancies(
			Principal principal, Model model,
			@Valid @ModelAttribute("vacancy") Vacancy vacancy) {
		User currentUser = userService.findByUsername(principal.getName());
		model.addAttribute("currentUser", currentUser);
		
		model.addAttribute("vacanciesInState", vacancyService.findVacanciesByState(currentUser.getState()));
		model.addAttribute("vacanciesOutOfState", vacancyService.findVacanciesNotInState(currentUser.getState()));

		return "vacancies.jsp";
	}
	
	@PostMapping("/vacancies")
	public String createVacancy(
			@Valid @ModelAttribute("vacancy") Vacancy vacancy, BindingResult result,
			Principal principal, Model model) {
		User currentUser = userService.findByUsername(principal.getName());
		
		model.addAttribute("vacanciesInState", vacancyService.findVacanciesByState(currentUser.getState()));
		model.addAttribute("vacanciesOutOfState", vacancyService.findVacanciesNotInState(currentUser.getState()));

		if (result.hasErrors()) {
			return "vacancies.jsp";
		} else {
			vacancy.setHost(currentUser);
			vacancyService.createVacancy(vacancy);
			return "redirect:/vacancies";
		}
	}
	
	@GetMapping("/vacancies/{id}")
	public String pageVacancy(
			@PathVariable("id") Long id, Model model,
			@Valid @ModelAttribute("comment") Comment comment) {
		Vacancy vacancy = vacancyService.findVacancyById(id);
		model.addAttribute("vacancy", vacancy);
		model.addAttribute("comments", commentService.findCommentsOfVacancy(vacancy));
		return "pageVacancy.jsp";
	}
	
	@PostMapping("/vacancies/{id}/newcomment")
	public String newComment(
			@PathVariable("id") Long id, Model model, Principal principal,
			@Valid @ModelAttribute("comment") Comment comment, BindingResult result) {
		Vacancy vacancy = vacancyService.findVacancyById(id);
		model.addAttribute("vacancy", vacancy);
		model.addAttribute("comments", commentService.findCommentsOfVacancy(vacancy));
		if (result.hasErrors()) {
			return "pageVacancy.jsp";
		} else {
			User currentUser = userService.findByUsername(principal.getName());
			comment.setAuthor(currentUser);
			comment.setVacancy(vacancy);
			commentService.createComment(comment);
			return "redirect:/vacancies/" + id;
		}
	}
	
	@GetMapping("/vacancies/{id}/join")
	public String joinVacancy(@PathVariable("id") Long id, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		Vacancy vacancy = vacancyService.findVacancyById(id);
		if (vacancy.getHost() != currentUser) {
			List<User> attendees = vacancy.getAttendees();
			attendees.add(currentUser);
			vacancy.setAttendees(attendees);
			vacancyService.createVacancy(vacancy);
		}
		return "redirect:/vacancies";
	}
	
	@GetMapping("/vacancies/{id}/cancel")
	public String unjoinVacancy(@PathVariable("id") Long id, Principal principal) {
		User currentUser = userService.findByUsername(principal.getName());
		Vacancy vacancy = vacancyService.findVacancyById(id);
		if (vacancy.getHost() != currentUser) {
			List<User> attendees = vacancy.getAttendees();
			attendees.remove(currentUser);
			vacancy.setAttendees(attendees);
			vacancyService.createVacancy(vacancy);
		}
		return "redirect:/vacancies";
	}
	
	@GetMapping("/vacancies/{id}/edit")
	public String editVacancy(
			@PathVariable("id") Long id, Principal principal, Model model) {
		User currentUser = userService.findByUsername(principal.getName());
		Vacancy vacancy = vacancyService.findVacancyById(id);
		if (vacancy.getHost() != currentUser || vacancy == null) {
			return "redirect:/vacancies";
		} else {

			model.addAttribute("vacancy", vacancy);
			return "editVacancy.jsp";
		}
	}
	
	@PostMapping("/vacancies/{id}/edit")
	public String updateVacancy(
			@PathVariable("id") Long id, Model model,
			@Valid @ModelAttribute("vacancy") Vacancy vacancy, BindingResult result) {

		if (result.hasErrors()) {
			return "editVacancy.jsp";
		} else {
			Vacancy currentVacancy = vacancyService.findVacancyById(id);
			currentVacancy.setName(vacancy.getName());
			currentVacancy.setCompany(vacancy.getCompany());
			currentVacancy.setState(vacancy.getState());
			vacancyService.createVacancy(currentVacancy);
			return "redirect:/vacancies/" + id;
		}
	}
	
	@GetMapping("/vacancies/{id}/delete")
	public String deleteVacancy(@PathVariable("id") Long id, Principal principal, Model model) {
		User currentUser = userService.findByUsername(principal.getName());
		Vacancy vacancy = vacancyService.findVacancyById(id);
		if (vacancy.getHost() == currentUser && vacancy != null) {
			vacancyService.deleteVacancy(id);
		}
		return "redirect:/vacancies";
	}
}
