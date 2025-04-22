package com.project.GreApp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.GreApp.model.QuestionWrapper;
import com.project.GreApp.model.Response;
import com.project.GreApp.model.Word;
import com.project.GreApp.model.WordWrapper;
import com.project.GreApp.service.AppService;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("home")
public class AppController {
	
	@Autowired
	AppService appService;
	
	@PostMapping("add/manual")
	public ResponseEntity<String> addWordManually(@RequestBody Word word){
		return appService.addWordManually(word);
	}
	
	@PostMapping("add/multiple")
	public ResponseEntity<List<String>> addMultipleWords(@RequestBody List<Word> words){
		return appService.addMultipleWords(words);
	}
	
	@PostMapping("add/auto")
	public ResponseEntity<Mono<String>> addWordAutomatically(@RequestBody Map<String, String> payload) {
	    String question = payload.get("question");
	    return appService.addWordAutomatically(question);
	}
	
	@GetMapping("get/allWords")
	public ResponseEntity<List<Word>> getAllWords(){
		return appService.getAllWords();
	}
	
	@GetMapping("get/word/id/{id}")
	public ResponseEntity<Optional<Word>> getWordbyId(@PathVariable Integer id){
		return appService.getWordbyId(id);
	}
	
	@GetMapping("get/word/{word}")
	public ResponseEntity<Optional<Word>> getWordbyName(@PathVariable String word){
		return appService.getWordbyName(word);
	}
	
	@GetMapping("question/generate/{numQ}")
	public ResponseEntity<List<Word>> getQuestions(@PathVariable Integer numQ) {
		try {
		return appService.getQuestions(numQ);
		}catch (Exception e) {
		    e.printStackTrace();
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
//	
//	@PostMapping("getQuestions")
//	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
//		return appService.getQuestionsFromId(questionIds);
//	}
//	
//	@PostMapping("getScore")
//	public ResponseEntity<Integer> getQuizScore(@RequestBody List<Response> responses){
//		return appService.getQuizScore(responses);
//	}
	
}
