package com.compass.ms_order.exeptions;

public class ErrorQuantityBelowZero extends RuntimeException {
    public ErrorQuantityBelowZero(String message) {
        super(message);
    }
}
