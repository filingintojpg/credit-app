package com.practice.decision_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.practice.decision_service", "com.practice.common"})
public class DecisionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecisionServiceApplication.class, args);
	}

}
