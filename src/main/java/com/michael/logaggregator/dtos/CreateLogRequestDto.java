package com.michael.logaggregator.dtos;

import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class CreateLogRequestDto {
    @NotBlank
    public String serviceName;

    @NotNull
    public LogLevel level;

    @NotBlank
    public String message;

    @NotNull
    public Long traceId;

    public Map<String, Object> metadata;

    public CreateLogRequestDto(){}

    public CreateLogRequestDto(String serviceName, LogLevel level, String message, Long traceId, Map<String, Object> metadata){
        this.serviceName = serviceName;
        this.level = level;
        this.message = message;
        this.traceId = traceId;
        this.metadata = metadata;
    }

    public LogEntry toEntity(){
        LogEntry log = new LogEntry();
        log.setLevel(getLevel());
        log.setMessage(getMessage());
        log.setMetadata(getMetadata());
        log.setServiceName(getServiceName());
        return log;
    }


    public @NotBlank String getServiceName() {
        return serviceName;
    }

    public void setServiceName(@NotBlank String serviceName) {
        this.serviceName = serviceName;
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
