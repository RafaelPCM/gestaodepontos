package com.logiquesistemas.gestaodepontos.dto;

import lombok.Data;

@Data
public class WorkdaySummaryDTO {
    private int totalWorkHours;
    private int completedWorkHours;
    private int exceededWorkHours;
    private int remainingWorkHours;
    private boolean isWorkdayComplete;
}
