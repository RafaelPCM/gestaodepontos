package com.logiquesistemas.gestaodepontos.controller;

public class UnauthorizedUserException extends RuntimeException {

  public UnauthorizedUserException(String message) {
    super(message);
  }
}
