package com.logiquesistemas.gestaodepontos.enums;

public enum WorkdayType {
    SIX_HOUR_CONTINUOUS("6 horas contínuas", 6, 0),
    EIGHT_HOUR_WITH_BREAK("8 horas com pausa para o almoço", 8, 1);  // Added a field for lunch break duration (in hours)
  
    private final String description;
    private final int totalWorkHours;
    private final int lunchBreakDuration;  // New field
  
    WorkdayType(String description, int totalWorkHours, int lunchBreakDuration) {
      this.description = description;
      this.totalWorkHours = totalWorkHours;
      this.lunchBreakDuration = lunchBreakDuration;
    }
  
    public String getDescription() {
      return description;
    }
  
    public int getTotalWorkHours() {
      return totalWorkHours;
    }
  
    public int getLunchBreakDuration() {
      return lunchBreakDuration;
    }
  }
  
