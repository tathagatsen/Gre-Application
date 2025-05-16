package com.project.GreUserService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.GreUserService.model.Response;
import com.project.GreUserService.model.QuizDto;
import com.project.GreUserService.model.UserQuestions;

@FeignClient(name="GreQuizService",url = "http://localhost:8090")
public interface UserQuizInterface {
	
	@PostMapping("quiz/create")
	public ResponseEntity<List<Integer>> createQuiz(@RequestBody QuizDto quizDto);
	
	@GetMapping("quiz/get/{id}")
	public ResponseEntity<List<UserQuestions>> getQuizQuestions(@RequestParam Integer userId,@PathVariable("id") Integer quizId);
	
	@PostMapping("quiz/submit")
	public ResponseEntity<Integer> submitQuiz(@RequestBody List<Response> responses);
}
