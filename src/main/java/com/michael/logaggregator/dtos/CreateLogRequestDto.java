package com.michael.logaggregator.dtos;

import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateLogRequestDto {
    @NotBlank
    public String serviceName;

    @NotNull
    public LogLevel level;

    @NotBlank
    public String message;

    public String metadata;

    public CreateLogRequestDto(){}

    public CreateLogRequestDto(String serviceName, LogLevel level, String message, String metadata){
        this.serviceName = serviceName;
        this.level = level;
        this.message = message;
        this.metadata = metadata == null ? "" : metadata;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

}
