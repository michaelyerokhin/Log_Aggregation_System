package com.michael.logaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
@EntityScan(basePackages = "com.michael.logaggregator.model")
public class LogaggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogaggregatorApplication.class, args);
	}

}
