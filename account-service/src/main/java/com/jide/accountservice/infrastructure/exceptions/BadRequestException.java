package com.jide.accountservice.infrastructure.exceptions;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message){
        super(message);
    }
}
