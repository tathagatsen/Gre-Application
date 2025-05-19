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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.GreApp.model.QuestionWrapper;
import com.project.GreApp.model.Response;
import com.project.GreApp.model.Word;
import com.project.GreApp.model.WordDto;
import com.project.GreApp.model.WordFile;
import com.project.GreApp.service.AppService;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("home")
public class AppController {
	
	@Autowired
	AppService appService;
	
//	@PostMapping("add/manual")
//	public ResponseEntity<String> addWordManually(@RequestBody Word word){
//		return appService.addWordManually(word);
//	}
	@PostMapping("add/manual")
	public ResponseEntity<Integer> addWordManually(@RequestParam Integer userId,@RequestBody WordDto wordDto){
		return appService.addWordManually(userId,wordDto);
	}
	
	@PostMapping("add/multiple")
	public ResponseEntity<List<Integer>> addMultipleWords(@RequestParam Integer userId,@RequestBody List<WordDto> wordDto){
		return appService.addMultipleWords(userId,wordDto);
	}
	
	@PostMapping("add/auto")
	public ResponseEntity<Integer> addWordAutomatically(@RequestParam Integer userId,@RequestBody Map<String, String> payload) {
	    String question = payload.get("question");
	    return appService.addWordAutomatically(userId,question);
	}
	
	@PostMapping("add/file")
	public ResponseEntity<List<Integer>> addWordsFromFile(@RequestParam Integer userId,@RequestParam String path){
		return appService.addWordsFromFile(userId,path);
	}
	
	@PutMapping("add/wordForms/{word}")
	public String addWordForms(@PathVariable String word){
		return appService.addWordForms(word);
	}
	
	@PostMapping("add/wordCategory")
	public ResponseEntity<String> addWordCategory() {
		return appService.addWordCategory();
	}
	
	@GetMapping("get/category/{category}")
	public ResponseEntity<List<Word>> getWordsCategory(@PathVariable String category){
		return appService.getWordsCategory(category);
	}
	
	@GetMapping("get/allWords/{id}")
	public ResponseEntity<List<Word>> getAllWords(@PathVariable("id")Integer userId){
		return appService.getAllWords(userId);
	}
	
	@GetMapping("get/word/{userId}/{wordId}")
	public ResponseEntity<Optional<Word>> getWordbyId(@PathVariable Integer userId,@PathVariable Integer wordId){
		return appService.getWordbyId(userId,wordId);
	}
	
	@GetMapping("get/word/name/{userId}/{word}")
	public ResponseEntity<Optional<Word>> getWordbyName(@PathVariable Integer userId,@PathVariable String word){
		return appService.getWordbyName(userId,word);
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

	
}
