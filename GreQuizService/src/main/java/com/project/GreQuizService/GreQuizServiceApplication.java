package com.project.GreQuizService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GreQuizServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreQuizServiceApplication.class, args);
	}

}
