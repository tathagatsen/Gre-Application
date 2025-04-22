package com.project.GreQuestionService.controller;

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

import com.project.GreQuestionService.model.QuestionWrapper;
import com.project.GreQuestionService.model.Response;
import com.project.GreQuestionService.service.QuestionService;



@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired 
	QuestionService questionService;
	
	@GetMapping("generate/{numQ}")
	public ResponseEntity<List<Integer>> generateQuestions(@PathVariable Integer numQ) {
		System.out.println("2 HALLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOO");
		return questionService.generateQuestions(numQ);
	}
		
//	@PostMapping("getQuestions")
//	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
//		return questionService.getQuestionsFromId(questionIds);
//	}
//	
//	@PostMapping("getScore")
//	public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses){
//		return questionService.getQuizScore(responses);
//	}
}
