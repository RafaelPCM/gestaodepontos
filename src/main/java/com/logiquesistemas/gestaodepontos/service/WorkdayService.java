package com.logiquesistemas.gestaodepontos.service;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.logiquesistemas.gestaodepontos.dto.WorkdaySummaryDTO;
import com.logiquesistemas.gestaodepontos.enums.PointType;
import com.logiquesistemas.gestaodepontos.enums.WorkdayType;
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
        
        WorkdaySummaryDTO summary = getLastWorkdaySummary(userId, dateTimeRecordEntry);
        List<Workday> workdays = workdayRepository.findWorkdayByUserId(userId);
        PointType lastPointType = null;

        if (!workdays.isEmpty()) {
            for (Workday workday : workdays) {
                if (workday.getWorkdayEntries().get(workday.getWorkdayEntries().size() - 1).getDateTimeRecordEntry().isBefore(dateTimeRecordEntry)) {
                    lastPointType = workday.getWorkdayEntries().get(workday.getWorkdayEntries().size() - 1).getPointType();
                    break;
                }
            }
        } else {
            Workday workday = new Workday();
                workday.setUser(User.builder().id(userId).build());

                workday.setWorkdayEntries(Collections.singletonList(
                    WorkdayEntry.builder()
                        .dateTimeRecordEntry(dateTimeRecordEntry)
                        .workday(workday)
                        .pointType(getPointType(workday))
                        .build()));
                return workdayRepository.save(workday);
        }

        if (summary.isWorkdayComplete() && lastPointType == PointType.EXIT) {
            try {
                Workday workday = new Workday();
                workday.setUser(User.builder().id(userId).build());

                workday.setWorkdayEntries(Collections.singletonList(
                    WorkdayEntry.builder()
                        .dateTimeRecordEntry(dateTimeRecordEntry)
                        .workday(workday)
                        .pointType(getPointType(workday))
                        .build()));

                return workdayRepository.save(workday);
            } catch (Exception e) {
                System.err.println("Erro ao criar dia de trabalho: " + e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar dia de trabalho");
            }
        } else {
            String errorMessage = "Erro ao criar dia de trabalho: ";
            if (!summary.isWorkdayComplete()) {
                errorMessage += "Dia de trabalho nao finalizado";
            } else {
                errorMessage += "Ultimo dia de trabalho nao encontrado ou nao finalizado com saida";
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }


    private PointType getPointType(Workday workday) {
        List<WorkdayEntry> existingEntries = workdayEntryRepository.findWorkdayEntryByWorkdayId(workday.getId());
        if (existingEntries.isEmpty()) {
            return PointType.ENTRY;
        } else {
            return existingEntries.get(existingEntries.size() - 1).getPointType() == PointType.ENTRY ? PointType.EXIT : PointType.ENTRY;
        }
    }


    private WorkdaySummaryDTO createWorkdaySummaryDTO(Workday workday) {
        WorkdaySummaryDTO summary = new WorkdaySummaryDTO();

        double totalWorkHours = workday.getUser().getWorkdayType().getTotalWorkHours();

        double lunchBreak = workday.getUser().getWorkdayType().getLunchBreakDuration();

        double completedWorkHours = calculateWorkedHours(workday, lunchBreak);

        summary.setWorkday(workday);
        summary.setTotalWorkHours(totalWorkHours);
        summary.setCompletedWorkHours(completedWorkHours);
        summary.setWorkdayComplete(completedWorkHours >= totalWorkHours);

        if (completedWorkHours >= totalWorkHours) {
            summary.setExceededWorkHours(completedWorkHours - totalWorkHours);
        } else {
            summary.setRemainingWorkHours(totalWorkHours - completedWorkHours);
        }

        return summary;
    }


    //     if (workday.getUser().getWorkdayType() == WorkdayType.SIX_HOUR_CONTINUOUS) {
    //       if (pointType != PointType.ENTRY) {
    //         throw new IllegalArgumentException("First entry for a continuous workday must be ENTRY");
    //       }
    //     } else if (workday.getUser().getWorkdayType() == WorkdayType.EIGHT_HOUR_WITH_BREAK) {
    //       if (pointType != PointType.ENTRY || workdayEntryRepository.findWorkdayEntryByWorkdayId(workday.getId()).size() < 3) {
    //         throw new IllegalArgumentException("Eight-hour workday requires at least 3 entries (ENTRY-EXIT-ENTRY)");
    //       }
    //     }


    private double calculateWorkedHours(Workday workday, double lunchBreak) {
        double workHours = 0;
        double pause = 0;
        double pauseAndLunchBreak = 0;

        List<WorkdayEntry> entries = workday.getWorkdayEntries();
        Collections.sort(entries, (entry1, entry2) -> entry1.getDateTimeRecordEntry().compareTo(entry2.getDateTimeRecordEntry()));

        // lunchBreak é o tempo minimo que preciso ter de pausa
        if(workday.getUser().getWorkdayType() == WorkdayType.EIGHT_HOUR_WITH_BREAK) {
            // preciso pegar o tempo entre os EXIT e os proximos ENTRY e diminuir de lunchBreak ai vou saber se tirou o tempo minimo

            for (int i = 0; i < entries.size() - 1; i++) {
                LocalDateTime start = entries.get(i).getDateTimeRecordEntry();
                LocalDateTime end = entries.get(i + 1).getDateTimeRecordEntry();
    
                if (entries.get(i).getPointType() == PointType.EXIT && entries.get(i + 1).getPointType() == PointType.ENTRY) {
                    pause += java.time.Duration.between(start, end).toHours();
                }
                // conferir se ta certo
                pauseAndLunchBreak = pause;
                workHours += start.until(end, java.time.temporal.ChronoUnit.HOURS);
            }
    
            return workHours - pauseAndLunchBreak;
        } else {
            // nao precisa considerar o lunchBreak

            for (int i = 0; i < entries.size() - 1; i++) {
                LocalDateTime start = entries.get(i).getDateTimeRecordEntry();
                LocalDateTime end = entries.get(i + 1).getDateTimeRecordEntry();
    
                if (entries.get(i).getPointType() == PointType.EXIT && entries.get(i + 1).getPointType() == PointType.ENTRY) {
                    pause += java.time.Duration.between(start, end).toHours();
                }
    
                workHours += start.until(end, java.time.temporal.ChronoUnit.HOURS);
            }
    
            return workHours - pause;
        }
                
    }


    private Workday getLastWorkday(Long userId, LocalDateTime dateTime) {
        List<Workday> workdays = workdayRepository.findWorkdayByUserId(userId);
        return workdays.stream()
                .filter(w -> w.getWorkdayEntries().get(w.getWorkdayEntries().size() - 1).getDateTimeRecordEntry().isBefore(dateTime))
                .max(Comparator.comparing(w -> w.getWorkdayEntries().get(w.getWorkdayEntries().size() - 1).getDateTimeRecordEntry()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ultimo dia de trabalho nao encontrado ou ainda em progresso"));
    }
    
    public WorkdaySummaryDTO getLastWorkdaySummary(Long userId, LocalDateTime dateTime) {
        List<Workday> workdays = workdayRepository.findWorkdayByUserId(userId);
        if(workdays.isEmpty()) {
            return null;
        } else {
            Workday lastWorkday = workdays.stream()
                .filter(w -> w.getWorkdayEntries()
                    .get(w.getWorkdayEntries().size() - 1)
                    .getDateTimeRecordEntry().isBefore(dateTime))
                    .max(Comparator.comparing(w -> w.getWorkdayEntries()
                        .get(w.getWorkdayEntries().size() - 1)
                        .getDateTimeRecordEntry()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ultimo dia de trabalho nao encontrado"));

            return createWorkdaySummaryDTO(lastWorkday);
        }
        
    }

    public double calculateRemainingWorkHoursForLastWorkday(Long userId, LocalDateTime dateTime) {
        WorkdaySummaryDTO summary = getLastWorkdaySummary(userId, dateTime);
        return summary.getTotalWorkHours() - summary.getCompletedWorkHours();
    }

    public double calculateExceededWorkHoursForLastWorkday(Long userId, LocalDateTime dateTime) {
        WorkdaySummaryDTO summary = getLastWorkdaySummary(userId, dateTime);
        return Math.max(summary.getCompletedWorkHours() - summary.getTotalWorkHours(), 0);
    }

    public List<WorkdayEntry> listWorkdayEntriesForLastWorkday(Long userId, LocalDateTime dateTime) {
        Workday lastWorkday = getLastWorkday(userId, dateTime);
        return lastWorkday.getWorkdayEntries();
    }


    private Workday getCurrentWorkday(Long userId, LocalDateTime dateTime) {
        List<Workday> workdays = workdayRepository.findWorkdayByUserId(userId);
        return workdays.stream()
            .filter(w -> w.getWorkdayEntries().get(0).getDateTimeRecordEntry().toLocalDate().isEqual(dateTime.toLocalDate()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dia de trabalho atual nao encontrado ou nao iniciado"));
    }
    

    public WorkdaySummaryDTO getCurrentWorkdaySummary(Long userId, LocalDateTime dateTime) {
        List<Workday> workdays = workdayRepository.findWorkdayByUserId(userId);
        if (workdays.isEmpty()) {
            return null; // No workdays found
        } else {
            Workday currentWorkday = workdays.stream()
                .filter(w -> w.getWorkdayEntries().get(0).getDateTimeRecordEntry().toLocalDate().isEqual(dateTime.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dia de trabalho atual nao encontrado ou nao iniciado"));
    
            return createWorkdaySummaryDTO(currentWorkday);
        }
    }

    
    public double calculateRemainingWorkHoursForCurrentWorkday(Long userId, LocalDateTime dateTime) {
        WorkdaySummaryDTO summary = getCurrentWorkdaySummary(userId, dateTime);
        return summary.getTotalWorkHours() - summary.getCompletedWorkHours();
    }

    public double calculateExceededWorkHoursForCurrentWorkday(Long userId, LocalDateTime dateTime) {
        WorkdaySummaryDTO summary = getCurrentWorkdaySummary(userId, dateTime);
        return Math.max(summary.getCompletedWorkHours() - summary.getTotalWorkHours(), 0);
    }

    public List<WorkdayEntry> listWorkdayEntriesForCurrentWorkday(Long userId, LocalDateTime dateTime) {
        Workday currentWorkday = getCurrentWorkday(userId, dateTime);
        return currentWorkday.getWorkdayEntries();
    }

}