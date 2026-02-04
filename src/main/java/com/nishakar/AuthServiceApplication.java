package com.nishakar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AuthServiceApplication {

	static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
