package com.project.GreUserService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.GreUserService.dao.UserDao;
import com.project.GreUserService.feign.UserQuizInterface;
import com.project.GreUserService.model.AppUser;
import com.project.GreUserService.model.QuizDto;
import com.project.GreUserService.model.AppUser;
import com.project.GreUserService.model.UserDto;
import com.project.GreUserService.model.UserQuestions;
import com.project.GreUserService.model.UserQuiz;
import com.project.GreUserService.model.UserQuizResponse;
import com.project.GreUserService.model.Response;

import feign.FeignException;


@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserQuizInterface userQuizInterface;
	
//	@Autowired
//	UserWordInterface userWordInterface;
			
	public ResponseEntity<String> createUser(UserDto userDto) {
//		if(!userDao.existsByUserId(userDto.getId())) {
			AppUser user=new AppUser();
//			user.setUserId(userDto.getId());
			user.setUserEmailId(userDto.getUserEmailId());
			user.setUserName(userDto.getUserName());
			userDao.save(user);
			return new ResponseEntity<String>("user created successfully",HttpStatus.OK);
//		}
//		else {
//			return new ResponseEntity<String>("user already exists",HttpStatus.EXPECTATION_FAILED);
//		}
	}

	public ResponseEntity<String> createUserQuiz(QuizDto quizDto) {
		try
		{
			ResponseEntity<List<Integer>> quizResponse=userQuizInterface.createQuiz(quizDto);
		if (quizResponse.getBody()!=null) {
			AppUser u=userDao.findById(quizDto.getUserId()).get();
			u.setQuizIds(quizResponse.getBody());
			userDao.save(u);
			return new ResponseEntity<String>("Quiz Created Successfully for ",HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<String>("Quiz creation unsuccessful for ",HttpStatus.EXPECTATION_FAILED);
		}}
		catch (FeignException.Conflict e) {
	        return new ResponseEntity<>("Quiz with the same name already exists", HttpStatus.CONFLICT);
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while creating quiz", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	public ResponseEntity<List<UserQuestions>> getQuizQuestions(UserQuiz userQuiz) {
		ResponseEntity<List<UserQuestions>> quizQuestions=userQuizInterface.getQuizQuestions(userQuiz.getUserId(),userQuiz.getQuizId());
		return quizQuestions;
	}

	public ResponseEntity<Integer> getUserQuizScore(Integer userId,List<Response> responses) {
		ResponseEntity<Integer> score=userQuizInterface.submitQuiz(responses);
		return score;
	}
	
	
	
	
	
}
