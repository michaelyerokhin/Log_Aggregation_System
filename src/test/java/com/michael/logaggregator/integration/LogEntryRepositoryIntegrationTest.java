package com.michael.logaggregator.integration;

import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import com.michael.logaggregator.repository.LogEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LogEntryRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>
            ("postgres:18")
            .withDatabaseName("log_aggregation")
            .withUsername("postgres")
            .withPassword("finishingThisTime13274");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private LogEntryRepository logRepository;

    @Test
    void testSaveAndFetch() {
        Map<String, Integer> internalData = new HashMap<>();
        internalData.put("Status code:", 400);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Metadata: ", internalData);
        LogEntry log = new LogEntry(
                "AWS",
                LogLevel.WARNING,
                "Integration Test: Success",
                1000L,
                metadata
        );
        logRepository.save(log);

        LogEntry foundById = logRepository.findById(log.getId());;
        List<LogEntry> foundByAllVisibleParameters = logRepository.findByLevelAndServiceNameAndTraceIdAndMessage(log.getLevel(), log.getServiceName(), log.getTraceId(), log.getMessage());


        assertThat(foundById).isNotNull();
        assertThat(foundById.getMessage()).isEqualTo("Integration Test: Success");
        System.out.println(foundById.getMetadata());

        assertThat(foundByAllVisibleParameters).isNotNull();
        assertThat(foundByAllVisibleParameters.getFirst().getLevel()).isEqualTo(LogLevel.WARNING);
    }
}

