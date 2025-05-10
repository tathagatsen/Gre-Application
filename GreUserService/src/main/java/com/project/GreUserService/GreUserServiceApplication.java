package com.project.GreUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GreUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreUserServiceApplication.class, args);
	}

}
