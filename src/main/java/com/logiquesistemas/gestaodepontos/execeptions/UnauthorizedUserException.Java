package com.logiquesistemas.gestaodepontos.exceptions;

public class UnauthorizedUserException extends RuntimeException {

  public UnauthorizedUserException(String message) {
    super(message);
  }
}
