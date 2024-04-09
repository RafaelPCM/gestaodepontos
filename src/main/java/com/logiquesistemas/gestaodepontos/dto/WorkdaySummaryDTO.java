package com.logiquesistemas.gestaodepontos.dto;

import com.logiquesistemas.gestaodepontos.model.Workday;

import lombok.Data;

@Data
public class WorkdaySummaryDTO {
    private Workday workday;
    private double totalWorkHours;
    private double completedWorkHours;
    private double exceededWorkHours;
    private double remainingWorkHours;
    private boolean isWorkdayComplete;
}
