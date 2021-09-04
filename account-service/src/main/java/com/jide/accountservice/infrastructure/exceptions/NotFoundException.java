package com.jide.accountservice.infrastructure.exceptions;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
