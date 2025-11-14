package com.michael.logaggregator.repository;

import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface LogEntryRepository extends JpaRepository<LogEntry,Long> {
    List<LogEntry> findByLevelAndServiceNameAndTraceIdAndMessage(LogLevel level, String serviceName, Long traceId, String message);

    LogEntry findById(UUID id);



}
