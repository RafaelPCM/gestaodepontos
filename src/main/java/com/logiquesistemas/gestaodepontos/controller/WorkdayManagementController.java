package com.logiquesistemas.gestaodepontos.controller;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logiquesistemas.gestaodepontos.dto.WorkdaySummaryDTO;
import com.logiquesistemas.gestaodepontos.service.WorkdayService;

@RestController
@RequestMapping("/api")
public class WorkdayManagementController {
    @Autowired
    private WorkdayService workdayService;

    @GetMapping("/workdays/{userId}/summary")
    public WorkdaySummaryDTO getWorkdaySummary(@PathVariable Long userId,
                                               @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.getWorkdaySummary(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/workdays/{userId}/remainingHours")
    public int getRemainingWorkHours(@PathVariable Long userId,
                                     @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.calculateRemainingWorkHours(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/workdays/{userId}/exceededHours")
    public int getExceededWorkHours(@PathVariable Long userId,
                                     @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.calculateExceededWorkHours(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }
}