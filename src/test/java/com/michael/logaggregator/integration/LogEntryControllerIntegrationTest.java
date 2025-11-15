package com.michael.logaggregator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michael.logaggregator.TestSecurityConfig;
import com.michael.logaggregator.controller.LogController;
import com.michael.logaggregator.dtos.LogEntryDto;
import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import com.michael.logaggregator.repository.LogEntryRepository;
import com.michael.logaggregator.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(TestSecurityConfig.class)
class LogControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LogEntryRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18")
            .withDatabaseName("log_aggregation")
            .withUsername("postgres")
            .withPassword("finishingThisTime13274");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setup() {
        repo.deleteAll();
    }

    @Test
    void createLog_returns201_andPersists() throws Exception {
        LogEntryDto dto = new LogEntryDto("AWS", LogLevel.WARNING, "Controller integration test", 1000L, Map.of());
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                        post("/logs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Controller integration test"))
                .andExpect(jsonPath("$.serviceName").value("AWS"));
        // Check persistence in the REAL database
        List<LogEntry> allLogs = repo.findAll();
        assertThat(allLogs).isNotNull();
        assertThat(allLogs.getFirst().getMessage()).isEqualTo("Controller integration test");
    }
}

