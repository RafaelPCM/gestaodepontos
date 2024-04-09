package com.logiquesistemas.gestaodepontos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.enums.PointType;
import com.logiquesistemas.gestaodepontos.model.Workday;
import com.logiquesistemas.gestaodepontos.repository.WorkdayEntryRepository;


@Service
public class WorkdayEntryService {
    @Autowired
    private WorkdayEntryRepository workdayEntryRepository;

    
    public List<WorkdayEntry> findByWorkdayId(Long workdayId) {
        return workdayEntryRepository.findWorkdayEntryByWorkdayId(workdayId);
    }

    public WorkdayEntry findById(Long id) {
        return workdayEntryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado."));
    }

    public WorkdayEntry createWorkdayEntry(Long workdayId, LocalDateTime dateTimeRecordEntry) {
        WorkdayEntry workdayEntry = new WorkdayEntry();
        workdayEntry.setWorkday(Workday.builder().id(workdayId).build());
    
        List<WorkdayEntry> existingEntries = workdayEntryRepository.findWorkdayEntryByWorkdayId(workdayId);
        WorkdayEntry lastEntry = null;
        if (!existingEntries.isEmpty()) {
            lastEntry = existingEntries.get(existingEntries.size() - 1);
        }
    
        if (lastEntry == null) {
            workdayEntry.setPointType(PointType.ENTRY);
        } else {
            workdayEntry.setPointType(lastEntry.getPointType() == PointType.ENTRY ? PointType.EXIT : PointType.ENTRY);
        }
    
        workdayEntry.setDateTimeRecordEntry(dateTimeRecordEntry);
        return workdayEntryRepository.save(workdayEntry);
    }

    public List<WorkdayEntry> findAll() {
        return workdayEntryRepository.findAll();
    }

}