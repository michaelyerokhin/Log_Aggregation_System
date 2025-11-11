package com.michael.logaggregator.controller;

import com.michael.logaggregator.dtos.CreateLogRequestDto;
import com.michael.logaggregator.dtos.LogEntryDto;
import com.michael.logaggregator.dtos.LogRetrievalDto;
import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import com.michael.logaggregator.service.LogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService){
        this.logService = logService;
    }

    /**
     * @param logEntryDto
     * @return
     * Handles a POST request to add a log
     */
    @PostMapping
    public ResponseEntity<LogEntryDto> addLog(@Valid @RequestBody LogEntryDto logEntryDto) {
        LogEntryDto result =  logService.addSingleLog(logEntryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public ResponseEntity<List<LogEntryDto>> retrieveLogs(@Valid LogRetrievalDto logRetrievalDto){
        List<LogEntryDto> result = logService.retrieveLogs(logRetrievalDto);
        return ResponseEntity.status(HttpStatus.FOUND).body(result);
    }












}
