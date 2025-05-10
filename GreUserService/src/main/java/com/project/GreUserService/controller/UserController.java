package com.project.GreUserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.GreUserService.model.QuizDto;
import com.project.GreUserService.model.AppUser;
import com.project.GreUserService.model.UserDto;
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
	
}
