package com.michael.logaggregator.repository;

import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry,Long> {
    List<LogEntry> findByLevelAndServiceNameAndTimestampAndMessage(LogLevel level, String serviceName, LocalDateTime timestamp, String message);



}
