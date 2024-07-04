package com.example.chatspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
		"com.example.chatspring.repository"
})
@EnableMongoRepositories(basePackages = "com.example.chatspring.repository")
@SpringBootApplication
public class ChatspringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatspringApplication.class, args);
	}

}
