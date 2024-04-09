package com.logiquesistemas.gestaodepontos.controller;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logiquesistemas.gestaodepontos.dto.WorkdaySummaryDTO;
import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.service.WorkdayService;
@RestController
@RequestMapping("/workdays")
@CrossOrigin(origins = "*")
public class WorkdayManagementController {

    @Autowired
    private WorkdayService workdayService;

    @GetMapping("/last-summary/{userId}")
    public WorkdaySummaryDTO getLastWorkdaySummary(@PathVariable Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.getLastWorkdaySummary(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/last-remaining-hours/{userId}")
    public double calculateRemainingWorkHoursForLastWorkday(@PathVariable Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.calculateRemainingWorkHoursForLastWorkday(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/last-exceeded-hours/{userId}")
    public double calculateExceededWorkHoursForLastWorkday(@PathVariable Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.calculateExceededWorkHoursForLastWorkday(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/last-entries/{userId}")
    public List<WorkdayEntry> listWorkdayEntriesForLastWorkday(@PathVariable Long userId,  @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.listWorkdayEntriesForLastWorkday(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    
    @GetMapping("/current-summary/{userId}")
    public WorkdaySummaryDTO getCurrentWorkdaySummary(@PathVariable Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.getCurrentWorkdaySummary(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/current-remaining-hours/{userId}")
    public double calculateRemainingWorkHoursForCurrentWorkday(@PathVariable Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.calculateRemainingWorkHoursForCurrentWorkday(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/current-exceeded-hours/{userId}")
    public double calculateExceededWorkHoursForCurrentWorkday(@PathVariable Long userId, @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.calculateExceededWorkHoursForCurrentWorkday(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

    @GetMapping("/current-entries/{userId}")
    public List<WorkdayEntry> listWorkdayEntriesForCurrentWorkday(@PathVariable Long userId,  @RequestParam(required = false) LocalDateTime dateTime) {
        return workdayService.listWorkdayEntriesForCurrentWorkday(userId, dateTime != null ? dateTime : LocalDateTime.now());
    }

}