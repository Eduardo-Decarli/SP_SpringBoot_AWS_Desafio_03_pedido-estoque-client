package com.compass.ms_stock.exceptions;

public class ErrorQuantityBelowZero extends RuntimeException {
    public ErrorQuantityBelowZero(String message) {
        super(message);
    }
}
