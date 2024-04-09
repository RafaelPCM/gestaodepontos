package com.logiquesistemas.gestaodepontos.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.logiquesistemas.gestaodepontos.model.Workday;

@Repository
public interface WorkdayRepository extends JpaRepository<Workday, Long> {
    List<Workday> findWorkdayByUserId(Long userId);
    
    @Query("SELECT w FROM Workday w WHERE w.user.id = :userId AND EXISTS (SELECT we FROM WorkdayEntry we WHERE we.workday = w AND we.dateTimeRecordEntry = :dateTime)")
    List<Workday> findByUserIdAndSpecificDateTime(Long userId, LocalDateTime dateTime);
}

