package com.logiquesistemas.gestaodepontos.enums;

public enum UserType {
  ADMIN("Administrador"),
  COMMON("Comum");

  private final String description;

  UserType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
