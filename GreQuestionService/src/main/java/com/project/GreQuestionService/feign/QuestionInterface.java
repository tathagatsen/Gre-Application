package com.project.GreQuestionService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.GreQuestionService.model.QuestionWrapper;
import com.project.GreQuestionService.model.Response;
import com.project.GreQuestionService.model.Word;

@FeignClient(name="GreAPP",url="http://localhost:8080")
public interface QuestionInterface {
	
	@GetMapping("home/question/generate/{numQ}")
	public ResponseEntity<List<Word>> getQuestions(@PathVariable Integer numQ);
	
	@PostMapping("home/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);
	
	@PostMapping("home/getScore")
	public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses);
}
