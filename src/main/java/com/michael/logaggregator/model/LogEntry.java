package com.michael.logaggregator.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.michael.logaggregator.dtos.LogEntryDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "logs")
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "INSERTED_AT")
    private LocalDateTime timestamp;

    @Column(nullable = false, name = "SERVICE_NAME")
    private String serviceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "LOG_TYPE")
    private LogLevel level;

    @Column(nullable = false, name = "CONTENT")
    private String message;

    @Column(nullable = false, name = "TRACE_ID")
    private Long traceId;

    @Column(columnDefinition = "jsonb", name = "JSON_METADATA")
    private String metadata;

    public LogEntry(){}

    public LogEntry(String serviceName, LogLevel level, String message, String metadata) {
        this.serviceName = serviceName;
        this.level = level;
        this.message = message;
        this.metadata = metadata == null ? "" : metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry myEntity = (LogEntry) o;
        return Objects.equals(id, myEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** Getters and setters below */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}






