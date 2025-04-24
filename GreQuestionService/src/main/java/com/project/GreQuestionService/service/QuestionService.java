package com.project.GreQuestionService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.project.GreQuestionService.feign.QuestionInterface;
import com.project.GreQuestionService.dao.QuestionDao;
import com.project.GreQuestionService.model.QuestionWrapper;
import com.project.GreQuestionService.model.Response;
import com.project.GreQuestionService.model.Word;

import reactor.core.publisher.Mono;

import com.project.GreQuestionService.model.Question;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;
	
	@Autowired 
	QuestionInterface questionInterface;
	
	@Autowired
	GeminiService geminiService;


	public ResponseEntity<List<Integer>> generateQuestions(Integer numQ,Integer quiz_id) {
		try {
			System.out.println("Calling GreApp to fetch words...");
		    List<Word> words = questionInterface.getQuestions(numQ).getBody();
		    
		    System.out.println("GreApp response: " + words);
		    if (words == null || words.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    }

		    List<Integer> questionIds = new ArrayList<>();
		    for (Word word : words) {
		        Question question = new Question();
		        question.setWordId(word.getId());
		        question.setWord(word.getWord());
		        question.setDefinition(word.getDefinition());
		        question.setExample(word.getExample());
		        question.setQuizId(quiz_id);
		        Question saved = questionDao.save(question);
		        questionIds.add(saved.getId());

		    }
		    		    return new ResponseEntity<>(questionIds, HttpStatus.OK);
		} catch (Exception e) {
		    e.printStackTrace(); // Log the error
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers= new ArrayList<>();
		List<Question> questions=questionDao.findAllById(questionIds);
		 
//		for(Integer id:questionIds) {
//			questions.addAll(questionDao.findByQuizId(id));
//		}
		 	
		for(Question q:questions) {
			QuestionWrapper qw=new QuestionWrapper();
			qw.setId(q.getQuizId());
			qw.setWord(q.getWord());
			wrappers.add(qw);
		}
		
		return new ResponseEntity<>(wrappers,HttpStatus.OK);
	}

	public ResponseEntity<Integer> getQuizScore(List<Response> responses) {
		int right=0;
		for(Response r:responses) {
			Question q=questionDao.findById(r.getId()).get();
			boolean isEmptyorNot=r.getDefinition().isEmpty();
			System.out.println(isEmptyorNot);
			if(geminiService.isDefinitionCorrect(q.getWord(), q.getDefinition(),r.getDefinition()) && !isEmptyorNot) {
				right++;
			}
		}
		return new ResponseEntity<>(right,HttpStatus.OK);	
	}


}
