package com.logiquesistemas.gpjt.controller;

public class UnauthorizedUserException extends RuntimeException {

  public UnauthorizedUserException(String message) {
    super(message);
  }
}
