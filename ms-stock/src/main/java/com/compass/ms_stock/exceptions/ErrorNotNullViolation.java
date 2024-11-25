package com.compass.ms_stock.exceptions;

public class ErrorNotNullViolation extends RuntimeException {
  public ErrorNotNullViolation(String message) {
    super(message);
  }
}
