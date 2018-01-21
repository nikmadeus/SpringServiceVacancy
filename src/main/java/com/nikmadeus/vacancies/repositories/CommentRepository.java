package com.nikmadeus.vacancies.repositories;

import java.util.List;

import com.nikmadeus.vacancies.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nikmadeus.vacancies.entities.Vacancy;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findByVacancyOrderByCreatedAtAsc(Vacancy vacancy);
}
