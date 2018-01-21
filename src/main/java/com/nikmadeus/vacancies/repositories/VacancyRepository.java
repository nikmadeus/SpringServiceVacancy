package com.nikmadeus.vacancies.repositories;

import java.util.List;

import com.nikmadeus.vacancies.entities.Vacancy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
	List<Vacancy> findByState(String state);
	
	@Query("SELECT e FROM Vacancy e WHERE e.state != ?1")
	List<Vacancy> findVacanciesNotInState(String state);
}
