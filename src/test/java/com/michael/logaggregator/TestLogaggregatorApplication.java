package com.michael.logaggregator;

import org.springframework.boot.SpringApplication;

public class TestLogaggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.from(LogaggregatorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
