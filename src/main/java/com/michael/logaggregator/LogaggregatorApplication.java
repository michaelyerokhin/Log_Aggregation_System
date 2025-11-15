package com.michael.logaggregator;

import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import com.michael.logaggregator.repository.LogEntryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = "com.michael.logaggregator.model")
public class LogaggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogaggregatorApplication.class, args);
	}
/**
	@Bean
	CommandLineRunner runner(LogEntryRepository repository) {
		return args -> {

			LogEntry log = new LogEntry();
			log.setMessage("Whats good");
			log.setTraceId(150L);
			log.setLevel(LogLevel.SUCCESS);
			log.setServiceName("EC2");
			log.setMetadata(null);

			repository.save(log);
			List<LogEntry> saved = repository.findByLevelAndServiceNameAndTraceIdAndMessage(log.getLevel(), log.getServiceName(), log.getTraceId(), log.getMessage());
			System.out.println(saved.getFirst().getMessage());
		};
	}
	*/

}
