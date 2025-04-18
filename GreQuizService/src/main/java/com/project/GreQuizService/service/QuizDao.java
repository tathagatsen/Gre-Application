package com.project.GreQuizService.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.GreQuizService.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer>{
	
}
