package com.michael.logaggregator;

import com.michael.logaggregator.dtos.LogEntryDto;
import com.michael.logaggregator.dtos.LogRetrievalDto;
import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import com.michael.logaggregator.repository.LogEntryRepository;
import com.michael.logaggregator.service.LogService;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class LogServiceTest {

    @Mock
    private LogEntryRepository logEntryRepository;

    @InjectMocks
    private LogService logService;

    @Test
    public void checkAddSingleLog() {
        // given
        LogEntryDto logEntryDto = new LogEntryDto();
        logEntryDto.setServiceName("AWS");
        logEntryDto.setMessage("The message about some log");
        logEntryDto.setLevel(LogLevel.ERROR);
        logEntryDto.setMetadata("Random metadata");

        LogEntry savedLogEntry = logEntryDto.toEntity();
        savedLogEntry.setId(1234L);

        when(logEntryRepository.save(any(LogEntry.class))).thenReturn(savedLogEntry);

        // when
        LogEntryDto result = logService.addSingleLog(logEntryDto);

        // then
        assertNotNull(result);
        assertEquals("AWS", result.getServiceName());
        assertEquals(LogLevel.ERROR, result.getLevel());
        assertEquals("The message about some log", result.getMessage());
        verify(logEntryRepository, times(1)).save(any(LogEntry.class));
    }

    @Test
    public void checkRetrieveLogs(){
        //Incoming logRetrievalDto data
        LogRetrievalDto logRetrievalDto = new LogRetrievalDto();
        logRetrievalDto.setDate(LocalDateTime.now());
        logRetrievalDto.setLevel(LogLevel.WARNING);
        logRetrievalDto.setServiceName("EC2 Instance");

        // Retrieved LogEntry
        LogEntry dummyResult = new LogEntry();
        dummyResult.setTimestamp(logRetrievalDto.getDate());
        dummyResult.setLevel(logRetrievalDto.getLevel());
        dummyResult.setServiceName(logRetrievalDto.getServiceName());

        List<LogEntry> result = List.of(dummyResult);

        when(logEntryRepository.findByLevelAndServiceNameAndDateAndMessage(any(LogLevel.class), any(String.class), any(LocalDateTime.class), any(String.class))).thenReturn(result);

        List<LogEntryDto> output = logService.retrieveLogs(logRetrievalDto);

        assertNotNull(output);
        assertEquals("EC2 Instance", output.getFirst().getServiceName());
        assertEquals(LogLevel.WARNING, output.getFirst().getLevel());
        verify(logEntryRepository, times(1)).findByLevelAndServiceNameAndDateAndMessage(any(LogLevel.class), any(String.class), any(LocalDateTime.class), any(String.class));


    }



}

