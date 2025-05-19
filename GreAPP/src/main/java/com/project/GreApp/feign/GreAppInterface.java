package com.project.GreApp.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="python-api",url="http://127.0.0.1:5000")
public interface GreAppInterface {
	
	@GetMapping("/get-word-forms/{word}")
	public Map<String,List<String>> getWordsForms(@PathVariable String word);
	
	@GetMapping("/get-word-category/{word}")
	public String getWordCategory(@PathVariable String word);
	
	
}
