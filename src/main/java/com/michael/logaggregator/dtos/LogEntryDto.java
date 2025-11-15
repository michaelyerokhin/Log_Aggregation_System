package com.michael.logaggregator.dtos;


import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Map;

public class LogEntryDto {
    @NotBlank
    private String serviceName;

    @NotNull
    private LogLevel level;

    @NotBlank
    private String message;

    @NotNull
    private Long traceId;

    private Map<String, Object> metadata;

    public LogEntryDto(){}

    public LogEntry toEntity() {
        LogEntry logEntry = new LogEntry();
        logEntry.setServiceName(getServiceName());
        logEntry.setLevel(getLevel());
        logEntry.setMessage(getMessage());
        logEntry.setTraceId(getTraceId());
        logEntry.setMetadata(getMetadata());
        return logEntry;
    }

    public LogEntryDto(String serviceName, LogLevel level, String message, Long traceId, Map<String, Object> metadata) {
        this.serviceName = serviceName;
        this.level = level;
        this.message = message;
        this.traceId = traceId;
        this.metadata = metadata;
    }

    public static LogEntryDto toLogEntryDto(LogEntry logEntry){
        LogEntryDto logEntryDto = new LogEntryDto();
        logEntryDto.setServiceName(logEntry.getServiceName());
        logEntryDto.setLevel(logEntry.getLevel());
        logEntryDto.setMessage(logEntry.getMessage());
        logEntryDto.setTraceId(logEntry.getTraceId());
        logEntryDto.setMetadata(logEntry.getMetadata());
        return logEntryDto;
    }


    public @NotBlank String getServiceName() {
        return serviceName;
    }

    public @NotNull LogLevel getLevel() {
        return level;
    }

    public void setLevel(@NotNull LogLevel level) {
        this.level = level;
    }

    public @NotBlank String getMessage() {
        return message;
    }

    public void setMessage(@NotBlank String message) {
        this.message = message;
    }

    public void setServiceName(@NotBlank String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public @NotNull Long getTraceId() {
        return traceId;
    }

    public void setTraceId(@NotNull Long traceId) {
        this.traceId = traceId;
    }
}
