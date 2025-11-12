package com.michael.logaggregator.dtos;

import com.michael.logaggregator.model.LogLevel;

import java.time.LocalDateTime;
import java.util.Date;

public class LogRetrievalDto {
    public String serviceName;
    public LocalDateTime date;
    public LogLevel level;
    public String message;

    public LogRetrievalDto(){}

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
