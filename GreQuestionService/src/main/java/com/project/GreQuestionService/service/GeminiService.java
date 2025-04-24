package com.project.GreQuestionService.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class GeminiService {
	@Value("${gemini.api.key}")
    private String apiKey;
	
//	private static final String GEMINI_ENDPOINT =
//		    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isDefinitionCorrect(String word, String correctDefinition,String userDefinition) {
//        String prompt = String.format("is it correct to define '%s' as :%s\n ?"+"Respond with just 'yes' or 'no'."+"What is a computer?"
//            ,
//            word, userDefinition
//        );
    	String prompt = String.format(
                "The correct definition of '%s' is: %s.\nUser entered: %s\n" +
                "Is the user's definition semantically correct? Respond with just YES or NO.",
                word, correctDefinition, userDefinition
            );

        Map<String, Object> message = Map.of(
            "contents", List.of(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", prompt))
            ))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(message, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(geminiApiUrl, request, Map.class);
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            System.out.println(response);
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> candidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

                if (!parts.isEmpty()) {
                    String output = parts.get(0).get("text").toString();
                    System.out.println(output);
                    
                    return output.trim().toLowerCase().contains("yes"); 
                }
            }
        } catch (Exception e) {
            System.out.println("Error talking to Gemini: " + e.getMessage());
        }
        return false;
    }

}
