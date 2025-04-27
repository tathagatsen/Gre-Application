package com.project.GreApp;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="python-api",url="http://localhost:5000")
public interface GreAppInterface {
	
	@GetMapping("/get-words-from-file")
	public List<Map<String,String>> getWordsFromFile();
}
