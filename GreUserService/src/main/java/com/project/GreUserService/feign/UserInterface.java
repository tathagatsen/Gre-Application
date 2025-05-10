package com.project.GreUserService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.GreUserService.model.QuizDto;

@FeignClient(name="GreQuizService",url = "http://localhost:8090")
public interface UserInterface {
	
	@PostMapping("quiz/create")
	public ResponseEntity<List<Integer>> createQuiz(@RequestBody QuizDto quizDto);
	
	
}
