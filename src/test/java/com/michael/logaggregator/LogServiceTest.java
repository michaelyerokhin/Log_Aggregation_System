package com.michael.logaggregator;

import com.michael.logaggregator.dtos.LogEntryDto;
import com.michael.logaggregator.dtos.LogRetrievalDto;
import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import com.michael.logaggregator.repository.LogEntryRepository;
import com.michael.logaggregator.service.LogService;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        HashMap<String, Integer> httpsResponse = new HashMap<>();
        httpsResponse.put("HTTP Response", 200);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Metadata", httpsResponse);

        logEntryDto.setMetadata(metadata);

        LogEntry savedLogEntry = logEntryDto.toEntity();

        when(logEntryRepository.save(any(LogEntry.class))).thenReturn(savedLogEntry);

        // when
        LogEntryDto result = logService.addSingleLog(logEntryDto);

        // then
        assertNotNull(result);
        assertEquals("AWS", result.getServiceName());
        assertEquals(LogLevel.ERROR, result.getLevel());
        assertEquals("The message about some log", result.getMessage());
        assertEquals(httpsResponse, result.getMetadata().get("Metadata"));
        verify(logEntryRepository, times(1)).save(any(LogEntry.class));
    }

    @Test
    public void checkRetrieveLogs(){
        //Incoming logRetrievalDto data
        LogRetrievalDto logRetrievalDto = new LogRetrievalDto();
        logRetrievalDto.setTimestamp(LocalDateTime.now());
        logRetrievalDto.setLevel(LogLevel.WARNING);
        logRetrievalDto.setServiceName("EC2 Instance");

        // Retrieved LogEntry
        LogEntry dummyResult = new LogEntry();
        dummyResult.setTimestamp(logRetrievalDto.getTimestamp());
        dummyResult.setLevel(logRetrievalDto.getLevel());
        dummyResult.setServiceName(logRetrievalDto.getServiceName());

        List<LogEntry> result = List.of(dummyResult);

        when(logEntryRepository.findByLevelAndServiceNameAndTimestampAndMessage(any(LogLevel.class), any(String.class), any(LocalDateTime.class), any(String.class))).thenReturn(result);

        List<LogEntryDto> output = logService.retrieveLogs(logRetrievalDto);

        assertNotNull(output);
        assertEquals("EC2 Instance", output.getFirst().getServiceName());
        assertEquals(LogLevel.WARNING, output.getFirst().getLevel());
        verify(logEntryRepository, times(1)).findByLevelAndServiceNameAndTimestampAndMessage(any(LogLevel.class), any(String.class), any(LocalDateTime.class), any(String.class));


    }



}

