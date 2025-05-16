package com.project.GreQuizService.service;

import java.util.ArrayList;
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
import com.project.GreQuizService.model.UserQuestions;

@Service
public class QuizService {
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuizInterface quizInterface;
	
	public ResponseEntity<List<Integer>> createQuiz(String quizName, Integer numQ, Integer userId) {

		  if (quizDao.findByQuizNameAndUserId(quizName,userId) != null) {
		        return new ResponseEntity<>(HttpStatus.CONFLICT); // Duplicate quiz name
		    }

		    Quiz quiz = new Quiz();
		    quiz.setQuizName(quizName);
		    quiz.setUserId(userId);
		    quiz = quizDao.save(quiz);

		    ResponseEntity<List<Integer>> response = quizInterface.generateQuestions(numQ, quiz.getId());
		    if (response == null || response.getBody() == null || response.getBody().isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    }

		    quiz.setQueIds(response.getBody());
		    quizDao.save(quiz);

		    return new ResponseEntity<>(List.of(quiz.getId()), HttpStatus.OK);
		}

	public ResponseEntity<List<UserQuestions>> getQuizQuestions(Integer userId,Integer quizId) {
		Quiz quiz=quizDao.findById(quizId).get();
		List<Integer> questionIds=quiz.getQueIds();
		ResponseEntity<List<QuestionWrapper>> questions=quizInterface.getQuestionsFromId(questionIds);
		List<UserQuestions> userQuestions=new ArrayList<UserQuestions>();
		for(QuestionWrapper q:questions.getBody()) {
			UserQuestions uq=new UserQuestions();
			uq.setUserId(userId);
			uq.setQueId(q.getQueId());
			uq.setQuizId(q.getQuizId());
			uq.setWord(q.getWord());
			userQuestions.add(uq);
		}
		return new ResponseEntity<>(userQuestions,HttpStatus.OK);
	}

	public ResponseEntity<Integer> submitQuiz(List<Response> responses) {
		ResponseEntity<Integer> score=quizInterface.getQuizScore(responses);
		return score;
	}

	

}
