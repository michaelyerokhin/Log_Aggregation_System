package com.michael.logaggregator.unit;

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
        logRetrievalDto.setTraceId(1000L);
        logRetrievalDto.setLevel(LogLevel.WARNING);
        logRetrievalDto.setServiceName("EC2 Instance");
        logRetrievalDto.setMessage("Valid message");

        // Retrieved LogEntry
        LogEntry dummyResult = new LogEntry();
        dummyResult.setLevel(logRetrievalDto.getLevel());
        dummyResult.setServiceName(logRetrievalDto.getServiceName());
        dummyResult.setTraceId(logRetrievalDto.getTraceId());
        dummyResult.setMessage(logRetrievalDto.getMessage());


        List<LogEntry> result = List.of(dummyResult);

        when(logEntryRepository.findByLevelAndServiceNameAndTraceIdAndMessage(any(LogLevel.class), any(String.class), any(Long.class), any(String.class))).thenReturn(result);

        List<LogEntryDto> output = logService.retrieveLogs(logRetrievalDto);

        assertNotNull(output);
        assertEquals("EC2 Instance", output.getFirst().getServiceName());
        assertEquals(LogLevel.WARNING, output.getFirst().getLevel());
        assertEquals((Long) 1000L, output.getFirst().getTraceId());
        assertEquals("Valid message", result.getFirst().getMessage());
        verify(logEntryRepository, times(1)).findByLevelAndServiceNameAndTraceIdAndMessage(any(LogLevel.class), any(String.class), any(Long.class), any(String.class));


    }



}

