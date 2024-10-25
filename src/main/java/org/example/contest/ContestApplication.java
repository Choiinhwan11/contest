package org.example.contest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)


public class ContestApplication {

	public static void main(String[] args) {


		SpringApplication.run(ContestApplication.class, args);
	}
}
