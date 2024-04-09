package com.logiquesistemas.gestaodepontos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.repository.WorkdayEntryRepository;

@SpringBootTest
public class WorkdayEntryServiceTest {

    @InjectMocks
    private WorkdayEntryService workdayEntryService;

    @Mock
    private WorkdayEntryRepository workdayEntryRepository;

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

}