package com.logiquesistemas.gestaodepontos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.logiquesistemas.gestaodepontos.dto.WorkdaySummaryDTO;
import com.logiquesistemas.gestaodepontos.enums.PointType;
import com.logiquesistemas.gestaodepontos.enums.WorkdayType;
import com.logiquesistemas.gestaodepontos.model.User;
import com.logiquesistemas.gestaodepontos.model.Workday;
import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.repository.UserRepository;
import com.logiquesistemas.gestaodepontos.repository.WorkdayEntryRepository;
import com.logiquesistemas.gestaodepontos.repository.WorkdayRepository;

@ExtendWith(MockitoExtension.class)
public class WorkdayServiceTest {

    @Mock
    private WorkdayRepository workdayRepository;

    @Mock
    private WorkdayEntryRepository workdayEntryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WorkdayService workdayService;

    private User user;
    private Workday workday;
    private WorkdayEntry workdayEntry;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setWorkdayType(WorkdayType.EIGHT_HOUR_WITH_BREAK);

        workday = new Workday();
        workday.setId(1L);
        workday.setUser(user);

        workdayEntry = new WorkdayEntry();
        workdayEntry.setId(1L);
        workdayEntry.setWorkday(workday);
        workdayEntry.setDateTimeRecordEntry(LocalDateTime.now());
        workdayEntry.setPointType(PointType.ENTRY);
        List<WorkdayEntry> workdayEntries = new ArrayList<>();
        workdayEntries.add(workdayEntry);
        workday.setWorkdayEntries(workdayEntries);
    }

    @Test
    public void testFindByUserId() {
        when(workdayRepository.findWorkdayByUserId(1L)).thenReturn(List.of(workday));

        List<Workday> foundWorkdays = workdayService.findByUserId(1L);

        assertNotNull(foundWorkdays);
        assertEquals(1, foundWorkdays.size());
        assertEquals(workday, foundWorkdays.get(0));
    }



    @Test
    public void testCreateWorkday() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(workdayRepository.save(any(Workday.class))).thenReturn(workday);

        Workday createdWorkday = workdayService.createWorkday(1L, LocalDateTime.now());

        assertNotNull(createdWorkday);
        assertEquals(workday, createdWorkday);
    }

    @Test
    public void testGetCurrentWorkdaySummary_WhenWorkdaysIsEmpty() {
        when(workdayRepository.findWorkdayByUserId(any())).thenReturn(new ArrayList<>());

        WorkdaySummaryDTO summaryDTO = workdayService.getCurrentWorkdaySummary(1L, LocalDateTime.now());

        assertNull(summaryDTO);
    }

    @Test
    public void testGetCurrentWorkdaySummary_WhenWorkdaysIsNotEmpty() {
        List<Workday> workdays = new ArrayList<>();
        workdays.add(workday);
        when(workdayRepository.findWorkdayByUserId(any())).thenReturn(workdays);

        WorkdaySummaryDTO summaryDTO = workdayService.getCurrentWorkdaySummary(1L, LocalDateTime.now());

        assertNotNull(summaryDTO);
    }

    @Test
    public void testGetCurrentWorkdaySummary_WhenWorkdayNotFound() {
        List<Workday> workdays = new ArrayList<>();
        workdays.add(workday);
        when(workdayRepository.findWorkdayByUserId(any())).thenReturn(workdays);

        LocalDateTime differentDate = LocalDateTime.now().minusDays(1);
        assertThrows(ResponseStatusException.class, () -> workdayService.getCurrentWorkdaySummary(1L, differentDate));
    }

}
