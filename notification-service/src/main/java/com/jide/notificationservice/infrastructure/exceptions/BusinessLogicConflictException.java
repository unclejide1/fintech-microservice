package com.jide.notificationservice.infrastructure.exceptions;

public class BusinessLogicConflictException extends RuntimeException{
    public BusinessLogicConflictException(String message){
        super(message);
    }
}
