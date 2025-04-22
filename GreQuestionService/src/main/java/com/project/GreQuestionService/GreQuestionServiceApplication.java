package com.project.GreQuestionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GreQuestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreQuestionServiceApplication.class, args);
	}

}
