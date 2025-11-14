package com.michael.logaggregator.dtos;

import com.michael.logaggregator.model.LogLevel;

import java.time.LocalDateTime;
import java.util.Date;

public class LogRetrievalDto {
    public String serviceName;
    public LogLevel level;
    public Long traceId;
    public String message;

    public LogRetrievalDto(){}

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getTraceId() {
        return traceId;
    }

    public void setTraceId(Long traceId) {
        this.traceId = traceId;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
