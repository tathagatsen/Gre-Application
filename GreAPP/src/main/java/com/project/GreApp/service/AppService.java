package com.project.GreApp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.GreApp.feign.GreAppInterface;
import com.project.GreApp.dao.AppDao;
import com.project.GreApp.model.QuestionWrapper;
import com.project.GreApp.model.Response;
import com.project.GreApp.model.Word;
import com.project.GreApp.model.WordDto;
import com.project.GreApp.model.WordFile;


import reactor.core.publisher.Mono;
@Service
public class AppService {
	
	@Autowired
	AppDao appDao;
	
	@Autowired
	GreAppInterface appInterface;
	
	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	
	private final WebClient webClient;
	
	public AppService(WebClient.Builder webClient) {
		super();
		this.webClient = webClient.build();
	}
	
	public ResponseEntity<Integer> addWordManually(Integer userId,WordDto wordDto) {
		String wordStr=wordDto.getWord().toLowerCase();
		if(!appDao.existsByWordAndUserId(wordStr,userId)) {
			Word word=new Word();
			word.setWord(wordStr);
			word.setDefinition(wordDto.getDefinition());
			word.setExample(wordDto.getExample());
			word.setUserId(userId);
			appDao.save(word);
			int wordId=word.getId();
		return new ResponseEntity<>(wordId,HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	public ResponseEntity<List<Integer>> addMultipleWords(Integer userId,List<WordDto> wordDto) {
		List<Integer> wordIds=new ArrayList<Integer>();
		for(WordDto w:wordDto) {
			String wordStr=w.getWord().toLowerCase();
			if (!appDao.existsByWordAndUserId(wordStr,userId)) {
				Word word=new Word();
				word.setWord(wordStr);
				word.setDefinition(w.getDefinition());
				word.setExample(w.getExample());
				word.setUserId(userId);
				appDao.save(word);
				wordIds.add(word.getId());
		    } 
		}
		return new ResponseEntity<>(wordIds,HttpStatus.CREATED);
	}
	
	public ResponseEntity<List<Integer>> addWordsFromFile(Integer userId,String path) {
		List<Map<String, String>> words;
		List<Integer> wordIds=new ArrayList<Integer>();
		try {
			words = readWordsFromFile(path);
			
			for(Map<String, String> w:words) {
				String wordStr=w.get("word").toLowerCase();
				if (!appDao.existsByWordAndUserId(wordStr,userId)) {
					Word word=new Word();
					word.setDefinition(w.get("definition"));
					word.setWord(wordStr);
					word.setExample(w.get("example"));
					word.setUserId(userId);
					appDao.save(word);
					String wordForm=addWordForms(w.get("word").toLowerCase());
					word.setWordForms(wordForm);
					appDao.save(word);
					wordIds.add(word.getId());
				}
				else {
					System.out.println(w.get("word")+" already exists in the database.");
				}
			}
			return new ResponseEntity<>(wordIds,HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);
	}
	
	private List<Map<String, String>> readWordsFromFile(String filepath) throws IOException {
	    List<Map<String, String>> wordList = new ArrayList<>();

//	    Path path = Paths.get("C:\\Users\\Snehasish Sengupta\\git\\repository\\GreAPP\\src\\main\\resources\\show.txt"); // or give full path if needed
	    Path path=Paths.get(filepath);
	    String content = Files.readString(path);

	    // Same regex pattern
	    Pattern pattern = Pattern.compile("\\d+\\.\\s+\\*\\*(.*?)\\*\\* – (.*?)\\s+_([^_]+)_", Pattern.DOTALL);
	    Matcher matcher = pattern.matcher(content);

	    while (matcher.find()) {
	        Map<String, String> wordMap = new HashMap<>();
	        wordMap.put("word", matcher.group(1).trim());
	        wordMap.put("definition", matcher.group(2).trim());
	        wordMap.put("example", matcher.group(3).trim());
	        wordList.add(wordMap);
	    }
	    return wordList;
	}
	
//	public ResponseEntity<Mono<Integer>> addWordAutomatically(Integer userId,String question) {
////		prompt 
////		{
////			  "question": "Give me a JSON array containing an object for the word \"precarious\" with the fields: \"word\", and \"definitions\" (where \"definitions\" is a list of objects containing \"definition\" and \"example\" fields). Format the response inside triple backticks as JSON."
////			}
//
//	    Map<String, Object> requestBody = Map.of(
//	        "contents", new Object[] {
//	            Map.of("parts", new Object[] {
//	                Map.of("text", question)
//	            })
//	        }
//	    );
//
//	    Mono<String> response = webClient.post()
//	        .uri(geminiApiUrl + geminiApiKey)
//	        .header("Content-Type", "application/json")
//	        .bodyValue(requestBody)
//	        .retrieve()
//	        .bodyToMono(String.class);
//	    Mono<Integer> wId=0;
//	    Mono<String> result = handleGeminiResponse(userId,response)
//	        .flatMap(word -> {
//	        	wId=word.getId();
//	            appDao.save(word); // Save parsed Word object
//	            return Mono.just("success");
//	        })
//	        .onErrorResume(e -> {
//	            e.printStackTrace();
//	            return Mono.just("error: " + e.getMessage());
//	        });
//
//	    return ResponseEntity.status(HttpStatus.CREATED).body(wId);
//	}
	public ResponseEntity<Integer> addWordAutomatically(Integer userId, String question) {
//		prompt 
//	{
//	  "question": "Give me a JSON array containing an object for the word \"precarious\" with the fields: \"word\", and \"definitions\" (where \"definitions\" is a list of objects containing \"definition\" and \"example\" fields). Format the response inside triple backticks as JSON."
//		}
		Map<String, Object> requestBody = Map.of(
	        "contents", new Object[] {
	            Map.of("parts", new Object[] {
	                Map.of("text", question)
	            })
	        }
	    );

	    String response = webClient.post()
	        .uri(geminiApiUrl + geminiApiKey)
	        .header("Content-Type", "application/json")
	        .bodyValue(requestBody)
	        .retrieve()
	        .bodyToMono(String.class)
	        .block(); // Convert Mono<String> to String

	    try {
	        Word word = handleGeminiResponse(userId, response); // Pass plain String
	        appDao.save(word);
	        return ResponseEntity.status(HttpStatus.CREATED).body(word.getId());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(-1);
	    }
	}
	public Word handleGeminiResponse(Integer userId, String response) throws Exception {
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode rootNode = mapper.readTree(response);
	    JsonNode candidates = rootNode.path("candidates");

	    if (!candidates.isArray() || candidates.size() == 0) {
	        throw new RuntimeException("No candidates found in response");
	    }

	    JsonNode contentNode = candidates.get(0).path("content");
	    JsonNode partsNode = contentNode.path("parts");

	    if (!partsNode.isArray() || partsNode.size() == 0) {
	        throw new RuntimeException("No parts found in content");
	    }

	    JsonNode textNode = partsNode.get(0).path("text");
	    String rawText = textNode.asText();

	    if (rawText == null || rawText.isEmpty()) {
	        throw new RuntimeException("No text found in response");
	    }

	    String cleanedJson = rawText.replaceAll("(?s)```json|```", "").trim();
	    JsonNode extractedJson = mapper.readTree(cleanedJson);

	    if (extractedJson.isArray() && extractedJson.size() > 0) {
	        extractedJson = extractedJson.get(0);
	    } else {
	        throw new RuntimeException("Expected JSON array but got something else or empty array");
	    }

	    

	    String word = extractedJson.path("word").asText();
	    if (word == null || word.isEmpty()) {
	        word = extractedJson.path("Word").asText();
	    }
	    String wordStr=word.toLowerCase();
	    if(!appDao.existsByWordAndUserId(wordStr,userId)) {
	    	Word w = new Word();
		    w.setUserId(userId);
		    w.setWord(wordStr);

		    JsonNode definitions = extractedJson.path("definitions");
		    if (!definitions.isArray() || definitions.size() == 0) {
		        throw new RuntimeException("No definitions found");
		    }
	
		    JsonNode firstDef = definitions.get(0);
		    w.setDefinition(firstDef.path("definition").asText());
		    w.setExample(firstDef.path("example").asText());
		    
		    return w;
		}
		else {
			return null;
		}
	}
//	public Mono<Word> handleGeminiResponse(Integer userId,Mono<String> responseMono) {
//	    return responseMono.flatMap(response -> {
//	        try {
//	            ObjectMapper mapper = new ObjectMapper();
//	            JsonNode rootNode = mapper.readTree(response);
//	            JsonNode candidates = rootNode.path("candidates");
//
//	            // Safety checks
//	            if (!candidates.isArray() || candidates.size() == 0) {
//	                return Mono.error(new RuntimeException("No candidates found in response"));
//	            }
//
//	            JsonNode contentNode = candidates.get(0).path("content");
//	            JsonNode partsNode = contentNode.path("parts");
//
//	            if (!partsNode.isArray() || partsNode.size() == 0) {
//	                return Mono.error(new RuntimeException("No parts found in content"));
//	            }
//
//	            JsonNode textNode = partsNode.get(0).path("text");
//	            String rawText = textNode.asText();
//
//	            if (rawText == null || rawText.isEmpty()) {
//	                return Mono.error(new RuntimeException("No text found in response"));
//	            }
//
//	            String cleanedJson = rawText.replaceAll("(?s)```json|```", "").trim();
//	            JsonNode extractedJson = mapper.readTree(cleanedJson);
//	         // If root is an array, take the first element
//	            if (extractedJson.isArray() && extractedJson.size() > 0) {
//	                extractedJson = extractedJson.get(0);
//	            } else {
//	                return Mono.error(new RuntimeException("Expected JSON array but got something else or empty array"));
//	            }
//
//
//	            System.out.println("Extracted JSON:\n" + extractedJson.toPrettyString());
//
//	            Word w = new Word();
//	            w.setUserId(userId);
//	            getAutoWordId(w.getId());
//	            String word = extractedJson.path("word").asText();
//	            if (word == null || word.isEmpty()) {
//	                word = extractedJson.path("Word").asText();
//	            }
//	            w.setWord(word.toLowerCase());
//	            
//	            JsonNode definitions = extractedJson.path("definitions");
//	            if (!definitions.isArray() || definitions.size() == 0) {
//	                return Mono.error(new RuntimeException("No definitions found"));
//	            }
//
//	            JsonNode firstDef = definitions.get(0);
//	            w.setDefinition(firstDef.path("definition").asText());
//	            w.setExample(extractedJson.path("definitions").get(0).path("example").asText());
//
//	            return Mono.just(w);
//	        } catch (Exception e) {
//	            e.printStackTrace(); 
//	            return Mono.error(new RuntimeException("Failed to parse Gemini response: " + e.getMessage()));
//	        }
//	    });
//	}
	
//	public ResponseEntity<Integer> addWordAuto(String question) {
//		HttpStatus status= (HttpStatus) addWordAutomatically(question).getStatusCode();
//		if(status==HttpStatus.CREATED) {
//			int id=getAutoId();
//		}
//		return null;
//	}
//	public Integer getAutoId() {
//		
//	}
//	public Integer getAutoWordId(Integer id) {
//		return id;
//	}
	
	public ResponseEntity<String> addWordCategory() {
		List<Word> words=appDao.findAll();
		for(Word w:words) {
			String response=appInterface.getWordCategory(w.getWord().toLowerCase());
			w.setCategory(response);
			appDao.save(w);
		}
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	
	

	public ResponseEntity<List<Word>> getWordsCategory(String category) {
		List<Word> words=appDao.findByCategory(category);
		return new ResponseEntity<List<Word>>(words,HttpStatus.OK);
	}
	
	
	public String addWordForms(String word) {
		
		if(appDao.existsByWord(word.toLowerCase())) {
			String wordFormsJson;
			Word w=appDao.findByWord(word.toLowerCase()).get();
			Map<String,List<String>> wordForms=appInterface.getWordsForms(word.toLowerCase());
			try {
	            ObjectMapper objectMapper = new ObjectMapper();
	            wordFormsJson = objectMapper.writeValueAsString(wordForms);
	            w.setWordForms(wordFormsJson);
	            return wordFormsJson;
	        } catch (Exception e) {
	            e.printStackTrace();
	            w.setWordForms("{}"); // fallback
	        }
			appDao.save(w);
//			return new ResponseEntity<String>("Updated",HttpStatus.OK);
			return "success";
		}else {
//			return new ResponseEntity<String>("word does not exist",HttpStatus.NOT_FOUND);
			return "word does not exist";
		}
	}

	public ResponseEntity<List<Word>> getAllWords(Integer userId) {
		return new ResponseEntity<>(appDao.findAllByUserId(userId),HttpStatus.OK);
	}

	public ResponseEntity<Optional<Word>> getWordbyId(Integer userId,Integer wordId) {
		try {
			return new ResponseEntity<>(appDao.findByIdAndWordId(userId,wordId),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<Optional<Word>> getWordbyName(Integer userId,String word) {
		try {
			return new ResponseEntity<>(appDao.findByWordAndUserId(userId,word.toLowerCase()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<List<Word>> getQuestions(Integer numQ,Integer userId) {
		List<Word> questions=appDao.findRandomQuestionsByIdAndUserId(userId,numQ);
		return new ResponseEntity<>(questions,HttpStatus.OK);
	}
	
}
