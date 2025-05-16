package com.project.GreQuizService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.GreQuizService.model.QuestionWrapper;
import com.project.GreQuizService.model.QuizDto;
import com.project.GreQuizService.model.Response;
import com.project.GreQuizService.model.UserQuestions;
import com.project.GreQuizService.service.QuizService;

@RestController
@RequestMapping("quiz")
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	@PostMapping("create")
	public ResponseEntity<List<Integer>> createQuiz(@RequestBody QuizDto quizDto){
		return quizService.createQuiz(quizDto.getQuizName(),quizDto.getNumQ(),quizDto.getUserId());
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<List<UserQuestions>> getQuizQuestions(@RequestParam Integer userId,@PathVariable("id") Integer quizId){
		return quizService.getQuizQuestions(userId,quizId);
	}
	
//	@PostMapping("submit/{id}")
//	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id,@RequestBody List<Response> responses){
//		return quizService.submitQuiz(responses);
//	}
	@PostMapping("submit")
	public ResponseEntity<Integer> submitQuiz(@RequestBody List<Response> responses){
		return quizService.submitQuiz(responses);
	}
}
