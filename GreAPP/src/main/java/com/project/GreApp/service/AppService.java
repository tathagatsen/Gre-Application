package com.project.GreApp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.GreApp.dao.AppDao;
import com.project.GreApp.model.QuestionWrapper;
import com.project.GreApp.model.Response;
import com.project.GreApp.model.Word;
import com.project.GreApp.model.WordWrapper;

import reactor.core.publisher.Mono;
@Service
public class AppService {
	
	@Autowired
	AppDao appDao;
	
	
	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	
	private final WebClient webClient;
	
	public AppService(WebClient.Builder webClient) {
		super();
		this.webClient = webClient.build();
	}
	
	public ResponseEntity<String> addWordManually(Word word) {
		appDao.save(word);
		return new ResponseEntity<>("success",HttpStatus.CREATED);
	}

	public ResponseEntity<Mono<String>> addWordAutomatically(String question) {
	    Map<String, Object> requestBody = Map.of(
	        "contents", new Object[] {
	            Map.of("parts", new Object[] {
	                Map.of("text", question)
	            })
	        }
	    );

	    Mono<String> response = webClient.post()
	        .uri(geminiApiUrl + geminiApiKey)
	        .header("Content-Type", "application/json")
	        .bodyValue(requestBody)
	        .retrieve()
	        .bodyToMono(String.class);

	    Mono<String> result = handleGeminiResponse(response)
	        .flatMap(word -> {
	            appDao.save(word); // Save parsed Word object
	            return Mono.just("success");
	        })
	        .onErrorResume(e -> {
	            e.printStackTrace();
	            return Mono.just("error: " + e.getMessage());
	        });

	    return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	
	public Mono<Word> handleGeminiResponse(Mono<String> responseMono) {
	    return responseMono.flatMap(response -> {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            JsonNode rootNode = mapper.readTree(response);
	            JsonNode candidates = rootNode.path("candidates");

	            // Safety checks
	            if (!candidates.isArray() || candidates.size() == 0) {
	                return Mono.error(new RuntimeException("No candidates found in response"));
	            }

	            JsonNode contentNode = candidates.get(0).path("content");
	            JsonNode partsNode = contentNode.path("parts");

	            if (!partsNode.isArray() || partsNode.size() == 0) {
	                return Mono.error(new RuntimeException("No parts found in content"));
	            }

	            JsonNode textNode = partsNode.get(0).path("text");
	            String rawText = textNode.asText();

	            if (rawText == null || rawText.isEmpty()) {
	                return Mono.error(new RuntimeException("No text found in response"));
	            }

	            String cleanedJson = rawText.replaceAll("(?s)```json|```", "").trim();
	            JsonNode extractedJson = mapper.readTree(cleanedJson);
	         // If root is an array, take the first element
	            if (extractedJson.isArray() && extractedJson.size() > 0) {
	                extractedJson = extractedJson.get(0);
	            } else {
	                return Mono.error(new RuntimeException("Expected JSON array but got something else or empty array"));
	            }


	            System.out.println("Extracted JSON:\n" + extractedJson.toPrettyString());

	            Word w = new Word();
	            String word = extractedJson.path("word").asText();
	            if (word == null || word.isEmpty()) {
	                word = extractedJson.path("Word").asText();
	            }
	            w.setWord(word);
	            
	            JsonNode definitions = extractedJson.path("definitions");
	            if (!definitions.isArray() || definitions.size() == 0) {
	                return Mono.error(new RuntimeException("No definitions found"));
	            }

	            JsonNode firstDef = definitions.get(0);
	            w.setDefinition(firstDef.path("definition").asText());
	            w.setExample(extractedJson.path("definitions").get(0).path("example").asText());

	            return Mono.just(w);
	        } catch (Exception e) {
	            e.printStackTrace(); // good for debugging
	            return Mono.error(new RuntimeException("Failed to parse Gemini response: " + e.getMessage()));
	        }
	    });
	}

	public ResponseEntity<List<Word>> getAllWords() {
		
		return new ResponseEntity<>(appDao.findAll(),HttpStatus.OK);
	}

	public ResponseEntity<List<String>> addMultipleWords(List<Word> words) {
		for(Word w:words) {
			if (!appDao.existsByWord(w.getWord())) {
		        appDao.save(w);
		    } 
		}
		return new ResponseEntity<List<String>>(HttpStatus.CREATED);
	}

	public ResponseEntity<Optional<Word>> getWordbyId(Integer id) {
		try {
			return new ResponseEntity<>(appDao.findById(id),HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Optional<Word>> getWordbyName(String word) {
		try {
			return new ResponseEntity<>(appDao.findByWord(word),HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<List<Word>> getQuestions(Integer numQ) {
		System.out.println("5HALLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOO");
		List<Word> questions=appDao.findRandomQuestionsById(numQ);
		System.out.println("6HALLLLLLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOOOOOOO");
		return new ResponseEntity<>(questions,HttpStatus.OK);
	}
//
//	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
//		List<QuestionWrapper> wrappers= new ArrayList<>();
//		List<Word> questions=new ArrayList<>();
//		
//		for(Integer id:questionIds) {
//			questions.add(appDao.findById(id).get());
//		}
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
//			Word word=appDao.findById(r.getId()).get();
//			if(r.getDefinition().equals(word.getDefinition())) {
//				right++;
//			}
//		}
//		return new ResponseEntity<>(right,HttpStatus.OK);
//	}
	
	


	
}
