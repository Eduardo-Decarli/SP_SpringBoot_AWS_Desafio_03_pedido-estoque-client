package com.compass.ms_client.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
