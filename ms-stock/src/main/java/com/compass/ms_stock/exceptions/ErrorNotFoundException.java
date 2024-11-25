package com.compass.ms_stock.exceptions;

public class ErrorNotFoundException extends RuntimeException{
    public ErrorNotFoundException(String msg) {
        super(msg);
    }
}
