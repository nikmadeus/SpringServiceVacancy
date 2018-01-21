package com.nikmadeus.vacancies.services;

import java.util.List;

import com.nikmadeus.vacancies.entities.Comment;
import com.nikmadeus.vacancies.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import com.nikmadeus.vacancies.entities.Vacancy;

@Service
public class CommentService {
	
	private CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public void createComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public List<Comment> findCommentsOfVacancy(Vacancy vacancy) {
		return commentRepository.findByVacancyOrderByCreatedAtAsc(vacancy);
	}

}
