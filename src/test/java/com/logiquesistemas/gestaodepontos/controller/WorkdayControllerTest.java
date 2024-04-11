package com.logiquesistemas.gestaodepontos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.logiquesistemas.gestaodepontos.dto.WorkdayEntryDTO;
import com.logiquesistemas.gestaodepontos.model.Workday;
import com.logiquesistemas.gestaodepontos.service.WorkdayService;

@SpringBootTest
public class WorkdayControllerTest {

    @Autowired
    private WorkdayController workdayController;

    @MockBean
    private WorkdayService workdayService;

    @Test
    public void testGetWorkdaysByUserId_ShouldReturnList() throws Exception {
        Long userId = 1L;
        List<Workday> mockList = Collections.singletonList(new Workday());

        Mockito.when(workdayService.findByUserId(userId)).thenReturn(mockList);

        List<Workday> response = workdayController.getWorkdaysByUserId(userId);

        assertEquals(mockList, response);
        verify(workdayService, times(1)).findByUserId(userId);
    }

    @Test
    public void testCreateWorkday_ShouldReturnWorkday() throws Exception {
        Long userId = 1L;
        LocalDateTime dateTimeRecordEntry = LocalDateTime.now();
        WorkdayEntryDTO workdayEntryDTO = new WorkdayEntryDTO();
        workdayEntryDTO.setDateTimeRecordEntry(dateTimeRecordEntry);

        Workday mockWorkday = new Workday();

        Mockito.when(workdayService.createWorkday(userId, dateTimeRecordEntry)).thenReturn(mockWorkday);

        Workday response = workdayController.createWorkday(userId, workdayEntryDTO);

        assertEquals(mockWorkday, response);
        verify(workdayService, times(1)).createWorkday(userId, dateTimeRecordEntry);
    }

    @Test
    public void testCreateWorkday_ShouldThrowException_WhenUserNotFound() throws Exception {
        Long userId = 1L;
        LocalDateTime dateTimeRecordEntry = LocalDateTime.now();
        WorkdayEntryDTO workdayEntryDTO = new WorkdayEntryDTO();
        workdayEntryDTO.setDateTimeRecordEntry(dateTimeRecordEntry);

        Mockito.when(workdayService.createWorkday(userId, dateTimeRecordEntry))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        
        assertThrows(ResponseStatusException.class, () -> workdayController.createWorkday(userId, workdayEntryDTO));
    }

    @Test
    public void testCreateWorkday_ShouldThrowException_OnInvalidWorkdayStatus() throws Exception {
        Long userId = 1L;
        LocalDateTime dateTimeRecordEntry = LocalDateTime.now();
        WorkdayEntryDTO workdayEntryDTO = new WorkdayEntryDTO();
        workdayEntryDTO.setDateTimeRecordEntry(dateTimeRecordEntry);

        Mockito.when(workdayService.createWorkday(userId, dateTimeRecordEntry))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid workday status"));


        assertThrows(ResponseStatusException.class, () -> workdayController.createWorkday(userId, workdayEntryDTO));
    }
}
