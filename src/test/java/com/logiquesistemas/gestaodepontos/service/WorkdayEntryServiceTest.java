package com.logiquesistemas.gestaodepontos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.logiquesistemas.gestaodepontos.enums.PointType;
import com.logiquesistemas.gestaodepontos.model.Workday;
import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.repository.WorkdayEntryRepository;
import com.logiquesistemas.gestaodepontos.repository.WorkdayRepository;

@SpringBootTest
public class WorkdayEntryServiceTest {

    @InjectMocks
    private WorkdayEntryService workdayEntryService;


    @Mock
    private WorkdayEntryRepository workdayEntryRepository;

    @Mock
    private WorkdayRepository workdayRepository;

    @Test
    public void testFindByWorkdayId_ShouldReturnList() throws Exception {
        Long workdayId = 1L;
        List<WorkdayEntry> mockList = Collections.singletonList(new WorkdayEntry());

        Mockito.when(workdayEntryRepository.findWorkdayEntryByWorkdayId(workdayId)).thenReturn(mockList);

        List<WorkdayEntry> response = workdayEntryService.findByWorkdayId(workdayId);

        assertEquals(mockList, response);
        verify(workdayEntryRepository, times(1)).findWorkdayEntryByWorkdayId(workdayId);
    }

    @Test
    public void testFindById_ShouldThrowException_WhenWorkdayEntryNotFound() throws Exception {
        Long id = 1L;

        Mockito.when(workdayEntryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> workdayEntryService.findById(id));
    }

    @Test
    public void testFindById_ShouldReturnWorkdayEntry() throws Exception {
        Long id = 1L;
        WorkdayEntry mockEntry = new WorkdayEntry();

        Mockito.when(workdayEntryRepository.findById(id)).thenReturn(Optional.of(mockEntry));

        WorkdayEntry response = workdayEntryService.findById(id);

        assertEquals(mockEntry, response);
        verify(workdayEntryRepository, times(1)).findById(id);
    }

    @Test
    public void testCreateWorkdayEntry() {

        Long workdayId = 1L;
        LocalDateTime dateTimeRecordEntry = LocalDateTime.now();

        List<WorkdayEntry> existingEntries = new ArrayList<>();
        WorkdayEntry lastEntry = new WorkdayEntry();
        lastEntry.setPointType(PointType.ENTRY);
        lastEntry.setDateTimeRecordEntry(dateTimeRecordEntry);
        existingEntries.add(lastEntry);

        when(workdayEntryRepository.findWorkdayEntryByWorkdayId(eq(workdayId)))
            .thenReturn(existingEntries);

        when(workdayRepository.findById(eq(workdayId)))
            .thenReturn(Optional.of(new Workday()));

        when(workdayEntryRepository.save(any(WorkdayEntry.class))).thenReturn(lastEntry);

        WorkdayEntry createdEntry = workdayEntryService.createWorkdayEntry(workdayId, dateTimeRecordEntry);

        assertNotNull(createdEntry);
        if (existingEntries.isEmpty()) {
            assertEquals(PointType.ENTRY, createdEntry.getPointType());
        }
        assertEquals(dateTimeRecordEntry, createdEntry.getDateTimeRecordEntry());
        verify(workdayEntryRepository, times(1)).save(any(WorkdayEntry.class));
    }
    
}
