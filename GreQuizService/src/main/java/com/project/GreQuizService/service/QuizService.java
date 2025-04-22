package com.project.GreQuizService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.GreQuizService.dao.QuizDao;
import com.project.GreQuizService.feign.QuizInterface;
import com.project.GreQuizService.model.QuestionWrapper;
import com.project.GreQuizService.model.Quiz;
import com.project.GreQuizService.model.Response;

@Service
public class QuizService {
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuizInterface quizInterface;
	
	public ResponseEntity<String> createQuiz(String quizName, Integer numQ) {
		if(quizDao.findByQuizName(quizName)==null) {
		Quiz quiz = new Quiz();
		quiz.setQuizName(quizName);
		quiz=quizDao.save(quiz);
		ResponseEntity<List<Integer>> response = quizInterface.generateQuestions(numQ,quiz.getId());
	    if (response == null || response.getBody().isEmpty()) {
	        return new ResponseEntity<>("Unable to generate enough questions.", HttpStatus.BAD_REQUEST);
	    }
	    List<Integer> questionIds = response.getBody();
	    quiz.setQueIds(questionIds);
	    quizDao.save(quiz);
	    return new ResponseEntity<>("Quiz created successfully.", HttpStatus.OK);}
		else {
			return new ResponseEntity<>("Duplcate Quiz Name",HttpStatus.CONFLICT);
		}
		}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Quiz quiz=quizDao.findById(id).get();
		List<Integer> questionIds=quiz.getQueIds();
		ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionIds);
		return questions;
	}

	public ResponseEntity<Integer> submitQuiz(Integer id, List<Response> responses) {
		ResponseEntity<Integer> score=quizInterface.getQuizScore(responses);
		return score;
	}

	

}
