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

    private Map<String, Object> metadata;

    public LogEntryDto(){}

    public LogEntry toEntity() {
        LogEntry logEntry = new LogEntry();
        logEntry.setServiceName(getServiceName());
        logEntry.setMetadata(getMetadata());
        logEntry.setLevel(getLevel());
        logEntry.setMessage(getMessage());
        return logEntry;
    }

    public static LogEntryDto toLogEntryDto(LogEntry logEntry){
        LogEntryDto logEntryDto = new LogEntryDto();
        logEntryDto.setLevel(logEntry.getLevel());
        logEntryDto.setMessage(logEntry.getMessage());
        logEntryDto.setMetadata(logEntry.getMetadata());
        logEntryDto.setServiceName(logEntry.getServiceName());
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
}
