package com.logiquesistemas.gestaodepontos.enums;

public enum PointType {
    ENTRY("Entrada"),
    EXIT("Saida");
    
    private String description;

    PointType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
