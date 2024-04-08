package com.logiquesistemas.gestaodepontos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;

@Repository
public interface WorkdayEntryRepository extends JpaRepository<WorkdayEntry, Long> {
    List<WorkdayEntry> findWorkdayEntryByWorkdayId(Long workdayId);
    
    @Query("SELECT we FROM WorkdayEntry we WHERE we.dateTimeRecordEntry = :dateTime")
    List<WorkdayEntry> findWorkdayEntryBySpecificDateTime(LocalDateTime dateTime);
}