package com.nikmadeus.vacancies.services;

import java.util.List;

import com.nikmadeus.vacancies.entities.Vacancy;
import com.nikmadeus.vacancies.repositories.VacancyRepository;
import org.springframework.stereotype.Service;

@Service
public class VacancyService {
	
	private VacancyRepository vacancyRepository;
	
	public VacancyService(VacancyRepository vacancyRepository) {
		this.vacancyRepository = vacancyRepository;
	}
	
	public void createVacancy(Vacancy vacancy) {
		vacancyRepository.save(vacancy);
	}
	
	public List<Vacancy> findVacanciesByState(String state) {
		return vacancyRepository.findByState(state);
	}
	
	public List<Vacancy> findVacanciesNotInState(String state) {
		return vacancyRepository.findVacanciesNotInState(state);
	}
	
	public Vacancy findVacancyById(Long id) {
		return vacancyRepository.findOne(id);
	}
	
	public void deleteVacancy(Long id) {
		vacancyRepository.delete(id);
	}

}
