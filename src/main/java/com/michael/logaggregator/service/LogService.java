package com.michael.logaggregator.service;

import com.michael.logaggregator.dtos.LogEntryDto;
import com.michael.logaggregator.dtos.LogRetrievalDto;
import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.repository.LogEntryRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LogService{

    public final LogEntryRepository logEntryRepository;

    public LogService(LogEntryRepository logEntryRepository){
        this.logEntryRepository = logEntryRepository;
    }

    public LogEntryDto addSingleLog(LogEntryDto logEntryDto){
        LogEntry logEntry = logEntryDto.toEntity();
        LogEntry result = logEntryRepository.save(logEntry);
        return LogEntryDto.toLogEntryDto(result);
    }

    public List<LogEntryDto> retrieveLogs(LogRetrievalDto logRetrievalDto){
        List<LogEntry> returnedEntries = logEntryRepository.findByLevelAndServiceNameAndTraceIdAndMessage(
                logRetrievalDto.getLevel(), logRetrievalDto.getServiceName(), logRetrievalDto.getTraceId(), logRetrievalDto.getMessage());
        return returnedEntries.stream().map(LogEntryDto::toLogEntryDto).toList();
    }

}
