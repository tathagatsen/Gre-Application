package com.project.GreQuizService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.GreQuizService.model.Response;
import com.project.GreQuizService.model.QuestionWrapper;

@FeignClient("GREAPP")
public interface QuizInterface {

	@GetMapping("home/generate")
	public ResponseEntity<List<Integer>> getQuestions(@RequestParam Integer numQ);
	
	@PostMapping("home/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);
	
	@PostMapping("home/getScore")
	public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses);
}
