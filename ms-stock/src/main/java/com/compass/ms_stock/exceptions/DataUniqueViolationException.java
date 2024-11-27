package com.compass.ms_stock.exceptions;

public class DataUniqueViolationException extends RuntimeException {
    public DataUniqueViolationException(String message) {
        super(message);
    }
}
