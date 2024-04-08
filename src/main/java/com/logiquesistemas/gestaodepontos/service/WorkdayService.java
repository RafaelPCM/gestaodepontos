package com.logiquesistemas.gestaodepontos.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logiquesistemas.gestaodepontos.dto.WorkdaySummaryDTO;
import com.logiquesistemas.gestaodepontos.enums.PointType;
import com.logiquesistemas.gestaodepontos.model.User;
import com.logiquesistemas.gestaodepontos.model.Workday;
import com.logiquesistemas.gestaodepontos.model.WorkdayEntry;
import com.logiquesistemas.gestaodepontos.repository.WorkdayEntryRepository;
import com.logiquesistemas.gestaodepontos.repository.WorkdayRepository;


@Service
public class WorkdayService {

    @Autowired
    private WorkdayRepository workdayRepository;

    @Autowired
    private WorkdayEntryRepository workdayEntryRepository;

    public List<Workday> findByUserId(Long userId) {
        return workdayRepository.findWorkdayByUserId(userId);
    }

    public void save(Workday workday) {
        workdayRepository.save(workday);
    }

    public Workday findById(Long workdayId) {
        return workdayRepository.findById(workdayId).orElse(null);
    }

    public Workday createWorkday(Long userId, LocalDateTime dateTimeRecordEntry) {
        Workday workday = new Workday();
        workday.setUser(User.builder().id(userId).build());
        workday.setWorkdayEntries(Collections.singletonList(WorkdayEntry.builder()
                .dateTimeRecordEntry(dateTimeRecordEntry)
                .workday(workday)
                .pointType(getPointType(workday))
                .build()));
        return workdayRepository.save(workday);
    }

    public WorkdaySummaryDTO getWorkdaySummary(Long userId, LocalDateTime dateTime) {
        List<Workday> workdays = workdayRepository.findWorkdayByUserId(userId);
        WorkdaySummaryDTO summary = new WorkdaySummaryDTO();
        int totalWorkHours = 0;
        int completedWorkHours = 0;
        int lunchBreak = 0;

        for (Workday workday : workdays) {
            totalWorkHours += workday.getUser().getWorkdayType().getTotalWorkHours();
            completedWorkHours += calculateWorkHours(workday, lunchBreak);
        }

        summary.setTotalWorkHours(totalWorkHours);
        summary.setCompletedWorkHours(completedWorkHours);
        summary.setWorkdayComplete(totalWorkHours == completedWorkHours);

        if (completedWorkHours > totalWorkHours) {
            summary.setExceededWorkHours(completedWorkHours - totalWorkHours);
        } else {
            summary.setRemainingWorkHours(totalWorkHours - completedWorkHours);
        }

        return summary;
    }

    public int calculateRemainingWorkHours(Long userId, LocalDateTime dateTime) {
        WorkdaySummaryDTO summary = getWorkdaySummary(userId, dateTime);
        return summary.getTotalWorkHours() - summary.getCompletedWorkHours();
    }

    public int calculateExceededWorkHours(Long userId, LocalDateTime dateTime) {
        WorkdaySummaryDTO summary = getWorkdaySummary(userId, dateTime);
        return Math.max(summary.getCompletedWorkHours() - summary.getTotalWorkHours(), 0);
    }

    private int calculateWorkHours(Workday workday, int lunchBreak) {
        int workHours = 0;
        List<WorkdayEntry> entries = workday.getWorkdayEntries();
        Collections.sort(entries, (entry1, entry2) -> entry1.getDateTimeRecordEntry().compareTo(entry2.getDateTimeRecordEntry()));

        for (int i = 0; i < entries.size() - 1; i++) {
            LocalDateTime start = entries.get(i).getDateTimeRecordEntry();
            LocalDateTime end = entries.get(i + 1).getDateTimeRecordEntry();

            if (entries.get(i).getPointType() == PointType.EXIT && entries.get(i + 1).getPointType() == PointType.ENTRY) {
                lunchBreak += java.time.Duration.between(start, end).toHours();
            }

            workHours += start.until(end, java.time.temporal.ChronoUnit.HOURS);
        }

        return workHours - lunchBreak;
    }

    private PointType getPointType(Workday workday) {
        List<WorkdayEntry> existingEntries = workdayEntryRepository.findWorkdayEntryByWorkdayId(workday.getId());
        if (existingEntries.isEmpty()) {
            return PointType.ENTRY;
        } else {
            return existingEntries.get(existingEntries.size() - 1).getPointType() == PointType.ENTRY ? PointType.EXIT : PointType.ENTRY;
        }
    }
}

