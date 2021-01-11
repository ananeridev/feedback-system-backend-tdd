package com.anabneri.feedbacksystem;

import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import com.anabneri.feedbacksystem.repository.FeedbackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

@SpringBootApplication
public class FeedbacksystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbacksystemApplication.class, args);
	}

}
