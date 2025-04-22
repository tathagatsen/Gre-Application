package com.project.GreQuizService.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.GreQuizService.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer>{
	
	Quiz findByQuizName(String quizName);
	
}
