package com.logiquesistemas.gestaodepontos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.logiquesistemas.gestaodepontos.dto.WorkdayEntryDTO;

import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.service.WorkdayEntryService;

@RestController
@RequestMapping("/workdayEntries")
public class WorkdayEntryController {
    @Autowired
    private WorkdayEntryService workdayEntryService;

    @GetMapping("/{workdayId}")
    public List<WorkdayEntry> getWorkdayEntriesByWorkdayId(@PathVariable Long workdayId) {
        return workdayEntryService.findByWorkdayId(workdayId);
    }

    @GetMapping
    public List<WorkdayEntry> getAllWorkdayEntries() {
        return workdayEntryService.findAll();
    }

    @PostMapping("/{workdayId}")
    public WorkdayEntry createWorkdayEntry(@PathVariable Long workdayId, @RequestBody WorkdayEntryDTO workdayEntryDTO) {
        return workdayEntryService.createWorkdayEntry(workdayId, workdayEntryDTO.getDateTimeRecordEntry());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkdayEntry(@PathVariable Long id) {
        workdayEntryService.deleteById(id);
    }
    
}