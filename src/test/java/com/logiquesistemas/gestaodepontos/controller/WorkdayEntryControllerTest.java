package com.logiquesistemas.gestaodepontos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;


import com.logiquesistemas.gestaodepontos.dto.WorkdayEntryDTO;
import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.service.WorkdayEntryService;


@SpringBootTest
public class WorkdayEntryControllerTest {

    @Autowired
    private WorkdayEntryController workdayEntryController;

    @MockBean
    private WorkdayEntryService workdayEntryService;

    @Test
    public void testGetWorkdayEntriesByWorkdayId_ShouldReturnList() throws Exception {
        Long workdayId = 1L;
        List<WorkdayEntry> mockList = Collections.singletonList(new WorkdayEntry());

        Mockito.when(workdayEntryService.findByWorkdayId(workdayId)).thenReturn(mockList);

        List<WorkdayEntry> response = workdayEntryController.getWorkdayEntriesByWorkdayId(workdayId);

        assertEquals(mockList, response);
        verify(workdayEntryService, times(1)).findByWorkdayId(workdayId);
    }

    @Test
    public void testGetAllWorkdayEntries_ShouldReturnList() throws Exception {
        List<WorkdayEntry> mockList = Collections.singletonList(new WorkdayEntry());

        Mockito.when(workdayEntryService.findAll()).thenReturn(mockList);

        List<WorkdayEntry> response = workdayEntryController.getAllWorkdayEntries();

        assertEquals(mockList, response);
        verify(workdayEntryService, times(1)).findAll();
    }

    @Test
    public void testCreateWorkdayEntry_ShouldThrowException_WhenWorkdayNotFound() throws Exception {
        Long workdayId = 1L;
        LocalDateTime dateTimeRecordEntry = LocalDateTime.now();
        WorkdayEntryDTO workdayEntryDTO = new WorkdayEntryDTO();

        
        Mockito.when(workdayEntryService.createWorkdayEntry(workdayId, dateTimeRecordEntry))
                .thenThrow(new ResourceNotFoundException("Workday not found"));

        workdayEntryController.createWorkdayEntry(workdayId, workdayEntryDTO);

        assertThrows(ResourceNotFoundException.class, () -> workdayEntryService.createWorkdayEntry(workdayId, dateTimeRecordEntry));
    }

    @Test
    public void testCreateWorkdayEntry_ShouldReturnWorkdayEntry() throws Exception {
        Long workdayId = 1L;
        LocalDateTime dateTimeRecordEntry = LocalDateTime.now();
        WorkdayEntry mockEntry = new WorkdayEntry();
        WorkdayEntryDTO workdayEntryDTO = new WorkdayEntryDTO();
        workdayEntryDTO.setDateTimeRecordEntry(dateTimeRecordEntry);

        Mockito.when(workdayEntryService.createWorkdayEntry(workdayId, workdayEntryDTO.getDateTimeRecordEntry())).thenReturn(mockEntry);

        WorkdayEntry response = workdayEntryController.createWorkdayEntry(workdayId, workdayEntryDTO);

        assertEquals(mockEntry, response);
        verify(workdayEntryService, times(1)).createWorkdayEntry(workdayId, workdayEntryDTO.getDateTimeRecordEntry());
    

    }
}
