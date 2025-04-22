package com.project.GreQuestionService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.GreQuestionService.QuestionInterface;
import com.project.GreQuestionService.dao.QuestionDao;
import com.project.GreQuestionService.model.QuestionWrapper;
import com.project.GreQuestionService.model.Response;
import com.project.GreQuestionService.model.Word;
import com.project.GreQuestionService.model.Question;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;
	
	@Autowired 
	QuestionInterface questionInterface;

	public ResponseEntity<List<Integer>> generateQuestions(Integer numQ) {
		try {
			System.out.println("Calling GreApp to fetch words...");
			System.out.println("3HALLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOO");
		    List<Word> words = questionInterface.getQuestions(numQ).getBody();
		    System.out.println("7HALLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOO");
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

		        Question saved = questionDao.save(question);
		        questionIds.add(saved.getId());

		    }
		    System.out.println("8HALLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOO");
		    return new ResponseEntity<>(questionIds, HttpStatus.OK);
		} catch (Exception e) {
		    e.printStackTrace(); // Log the error
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
//		List<QuestionWrapper> wrappers= new ArrayList<>();
//		List<Word> questions=new ArrayList<>();
//		
//		for(Integer id:questionIds) {
//			questions.add(questionDao.findById(id).get());
//		}
//		
//		for(Word w:questions) {
//			QuestionWrapper qw=new QuestionWrapper();
//			qw.setId(w.getId());
//			qw.setWord(w.getWord());
//			wrappers.add(qw);
//		}
//		
//		return new ResponseEntity<>(wrappers,HttpStatus.OK);
//	}
//
//	public ResponseEntity<Integer> getQuizScore(List<Response> responses) {
//		int right=0;
//		for(Response r:responses) {
//			Word word=questionInterface.findById(r.getId()).get();
//			if(r.getDefinition().equals(word.getDefinition())) {
//				right++;
//			}
//		}
//		return new ResponseEntity<>(right,HttpStatus.OK);
//	}


}
