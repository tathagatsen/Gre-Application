package com.project.GreUserService.feign;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.GreUserService.model.Word;
import com.project.GreUserService.model.WordDto;

import reactor.core.publisher.Mono;

@FeignClient(name="GreAPP",url="http://localhost:8080")
public interface UserWordInterface {
	
	@PostMapping("home/add/manual")
	public ResponseEntity<Integer> addWordManually(@RequestParam Integer userId,@RequestBody WordDto wordDto);
	
	@PostMapping("home/add/multiple")
	public ResponseEntity<List<Integer>> addMultipleWords(@RequestParam Integer userId,@RequestBody List<WordDto> wordDto);

	@PostMapping("home/add/auto")
	public ResponseEntity<Integer> addWordAutomatically(@RequestParam Integer userId,@RequestBody Map<String, String> payload);

	@PostMapping("home/add/file")
	public ResponseEntity<List<Integer>> addWordsFromFile(@RequestParam Integer userId,@RequestParam String path);

	@GetMapping("home/get/allWords/{id}")
	public ResponseEntity<List<Word>> getAllWords(@PathVariable("id")Integer userId);
	
	@GetMapping("home/get/word/{userId}/{wordId}")
	public ResponseEntity<Optional<Word>> getWordbyId(@PathVariable Integer userId,@PathVariable Integer wordId);
	
	@GetMapping("home/get/word/name/{userId}/{word}")
	public ResponseEntity<Optional<Word>> getWordbyName(@PathVariable Integer userId,@PathVariable String word);

}
