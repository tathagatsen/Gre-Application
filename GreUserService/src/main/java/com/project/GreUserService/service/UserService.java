package com.project.GreUserService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.GreUserService.dao.UserDao;
import com.project.GreUserService.feign.UserQuizInterface;
import com.project.GreUserService.feign.UserWordInterface;
import com.project.GreUserService.model.AppUser;
import com.project.GreUserService.model.QuizDto;
import com.project.GreUserService.model.AppUser;
import com.project.GreUserService.model.UserDto;
import com.project.GreUserService.model.UserQuestions;
import com.project.GreUserService.model.UserQuiz;
import com.project.GreUserService.model.UserQuizResponse;
import com.project.GreUserService.model.Word;
import com.project.GreUserService.model.WordDto;

import com.project.GreUserService.model.Response;

import feign.FeignException;


@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserQuizInterface userQuizInterface;
	
	@Autowired
	UserWordInterface userWordInterface;
			
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

	public ResponseEntity<String> addUserWordManually(Integer userId, WordDto wordDto) {
		ResponseEntity<Integer> wordId=userWordInterface.addWordManually(userId,wordDto);
		List<Integer> wordIds=new ArrayList<Integer>();
		AppUser user=userDao.findById(userId).get();
		wordIds.addAll(user.getWordIds());
		user.setWordIds(wordIds);
		wordIds.add(wordId.getBody());
		userDao.save(user);
		return new ResponseEntity<>("Word added",HttpStatus.CREATED);
	}

	public ResponseEntity<String> addUserMultipleWords(Integer userId, List<WordDto> wordDto) {
		ResponseEntity<List<Integer>> wordIds=userWordInterface.addMultipleWords(userId,wordDto);
		Optional<AppUser> optionalUser = userDao.findById(userId);
		AppUser user = optionalUser.get();
		List<Integer> updatedWordIds=new ArrayList<Integer>();
		if (user.getWordIds() != null) {
	        updatedWordIds.addAll(user.getWordIds());
	    }
		updatedWordIds.addAll(wordIds.getBody());
		user.setWordIds(updatedWordIds);
		userDao.save(user);
		return new ResponseEntity<>("words added",HttpStatus.CREATED);
	}

	public ResponseEntity<String> addUserWordAuto(Integer userId, Map<String, String> payload) {
		ResponseEntity<Integer> wordId=userWordInterface.addWordAutomatically(userId,payload);
		List<Integer> wordIds=new ArrayList<Integer>();
		AppUser user=userDao.findById(userId).get();
		wordIds.addAll(user.getWordIds());
		user.setWordIds(wordIds);
		wordIds.add(wordId.getBody());
		userDao.save(user);
		return new ResponseEntity<>("words added",HttpStatus.CREATED);
	}

	public ResponseEntity<String> addUserWordsFromFile(Integer userId) {
		String path="C:\\Users\\Snehasish Sengupta\\git\\repository\\GreUserService\\src\\main\\resources\\show.txt";
		ResponseEntity<List<Integer>> wordIds=userWordInterface.addWordsFromFile(userId,path);
		Optional<AppUser> optionalUser = userDao.findById(userId);
		AppUser user = optionalUser.get();
		List<Integer> updatedWordIds=new ArrayList<Integer>();
		if (user.getWordIds() != null) {
	        updatedWordIds.addAll(user.getWordIds());
	    }
		updatedWordIds.addAll(wordIds.getBody());
		user.setWordIds(updatedWordIds);
		userDao.save(user);
		return new ResponseEntity<String>("words added from file",HttpStatus.CREATED);
	}

	public ResponseEntity<List<Word>> getAllUserWords(Integer userId) {
		ResponseEntity<List<Word>> words=userWordInterface.getAllWords(userId);
		List<Word> wordlist=new ArrayList<Word>();
		for(Word w:words.getBody()) {
			Word nw=new Word();
			nw.setUserId(userId);
			nw.setWord(w.getWord());
			nw.setId(w.getId());
			nw.setDefinition(w.getDefinition());
			nw.setExample(w.getExample());
			wordlist.add(nw);
		}
		return new ResponseEntity<List<Word>>(wordlist,HttpStatus.OK);
	}

	public ResponseEntity<Optional<Word>> getUserWordById(Integer userId,Integer wordId) {
		ResponseEntity<Optional<Word>> wordResponse=userWordInterface.getWordbyId(userId, wordId);
		Optional<Word> w=wordResponse.getBody();
		Word word=w.get();
		Word nw=new Word();
		nw.setUserId(userId);
		nw.setWord(word.getWord());
		nw.setId(word.getId());
		nw.setDefinition(word.getDefinition());
		nw.setExample(word.getExample());
		return new ResponseEntity<Optional<Word>>(Optional.of(nw),HttpStatus.OK);
	}

	public ResponseEntity<Optional<Word>> getUserWordByName(Integer userId, String word) {
		ResponseEntity<Optional<Word>> wordResponse=userWordInterface.getWordbyName(userId, word.toLowerCase());
		Optional<Word> w=wordResponse.getBody();
		Word optionalW=w.get();
		Word nw=new Word();
		nw.setUserId(userId);
		nw.setWord(optionalW.getWord());
		nw.setId(optionalW.getId());
		nw.setDefinition(optionalW.getDefinition());
		nw.setExample(optionalW.getExample());
		return new ResponseEntity<Optional<Word>>(Optional.of(nw),HttpStatus.OK);
	}
	
}
