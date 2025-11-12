package com.michael.logaggregator.repository;

import com.michael.logaggregator.model.LogEntry;
import com.michael.logaggregator.model.LogLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry,Long> {
    List<LogEntry> findByLevelAndServiceNameAndDateAndMessage(LogLevel level, String serviceName, LocalDateTime date, String message);



}
