package com.project.GreApp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

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
	
	@GetMapping("allWords")
	public ResponseEntity<List<Word>> showAllWords(){
		return appService.showAllWords();
	}
}
