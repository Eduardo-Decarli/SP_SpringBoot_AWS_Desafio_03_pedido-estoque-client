package com.compass.ms_client.exceptions;

public class ErrorNotFoundException extends RuntimeException{
    public ErrorNotFoundException(String msg) {
        super(msg);
    }
}
