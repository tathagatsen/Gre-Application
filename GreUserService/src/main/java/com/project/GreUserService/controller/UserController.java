package com.project.GreUserService.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.GreUserService.model.QuizDto;
import com.project.GreUserService.model.Response;
import com.project.GreUserService.model.AppUser;
import com.project.GreUserService.model.UserDto;
import com.project.GreUserService.model.UserQuestions;
import com.project.GreUserService.model.UserQuiz;
import com.project.GreUserService.model.UserQuizResponse;
import com.project.GreUserService.model.Word;
import com.project.GreUserService.model.WordDto;
import com.project.GreUserService.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("create")
	public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
		return userService.createUser(userDto);
	}
	
	@PostMapping("create/quiz")
	public ResponseEntity<String> createUserQuiz(@RequestBody QuizDto quizDto){
		return userService.createUserQuiz(quizDto);
	}
	
	@PostMapping("get/quiz")
	public ResponseEntity<List<UserQuestions>> getQuizQuestions(@RequestBody UserQuiz userQuiz){
		return userService.getQuizQuestions(userQuiz);
	}
	
	@PostMapping("submit/{userId}")
	public ResponseEntity<Integer> getUserQuizScore(@PathVariable Integer userId,@RequestBody List<Response> responses){
		return userService.getUserQuizScore(userId,responses);
	}
	
	@PostMapping("add/word/{id}")
	public ResponseEntity<String> addUserWordManually(@PathVariable("id") Integer userId,@RequestBody WordDto wordDto){
		return userService.addUserWordManually(userId,wordDto);
	}
	
	@PostMapping("add/words/{id}")
	public ResponseEntity<String> addUserMultipleWords(@PathVariable("id") Integer userId,@RequestBody List<WordDto> wordDto){
		return userService.addUserMultipleWords(userId,wordDto);
	}
	
	@PostMapping("add/auto/{id}")
	public ResponseEntity<String> addUserWordAuto(@PathVariable("id") Integer userId,@RequestBody Map<String, String> payload){
		return userService.addUserWordAuto(userId,payload);
	}
	
	@PostMapping("add/file/{id}")
	public ResponseEntity<String> addUserWordsFromFile(@PathVariable("id") Integer userId){
		return userService.addUserWordsFromFile(userId);
	}
	
	@GetMapping("get/all/{id}")
	public ResponseEntity<List<Word>> getAllUserWords(@PathVariable("id")Integer userId){
		return userService.getAllUserWords(userId);
	}
	
	@GetMapping("get/word/{userId}/{wordId}")
	public ResponseEntity<Optional<Word>> getUserWordById(@PathVariable Integer userId,@PathVariable Integer wordId){
		return userService.getUserWordById(userId,wordId);
	}
	
	@GetMapping("get/word/name/{userId}/{word}")
	public ResponseEntity<Optional<Word>> getUserWordByName(@PathVariable Integer userId,@PathVariable String word){
		return userService.getUserWordByName(userId,word);
	}
	
}
