package com.project.GreQuizService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.GreQuizService.model.Response;
import com.project.GreQuizService.model.QuestionWrapper;

@FeignClient(name = "GreQuestionService", url = "http://localhost:8091")
public interface QuizInterface {

	@GetMapping("question/generate/{numQ}")
	public ResponseEntity<List<Integer>> generateQuestions(@PathVariable Integer numQ);
	
	@PostMapping("question/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);
	
	@PostMapping("question/getScore")
	public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses);
}
