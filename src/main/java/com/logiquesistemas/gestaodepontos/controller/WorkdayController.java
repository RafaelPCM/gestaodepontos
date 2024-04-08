package com.logiquesistemas.gestaodepontos.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logiquesistemas.gestaodepontos.dto.WorkdayEntryDTO;
import com.logiquesistemas.gestaodepontos.model.Workday;
import com.logiquesistemas.gestaodepontos.service.WorkdayService;
@RestController
@RequestMapping("/workdays")
public class WorkdayController {
    @Autowired
    private WorkdayService workdayService;

    @GetMapping("/{userId}")
    public List<Workday> getWorkdaysByUserId(@PathVariable Long userId) {
        return workdayService.findByUserId(userId);
    }

    @PostMapping("/{userId}")
    public Workday createWorkday(@PathVariable Long userId, @RequestBody WorkdayEntryDTO workdayEntryDTO) {
        return workdayService.createWorkday(userId, workdayEntryDTO.getDateTimeRecordEntry());
    }

}