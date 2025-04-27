package com.project.GreApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreAppApplication.class, args);
	}
}
