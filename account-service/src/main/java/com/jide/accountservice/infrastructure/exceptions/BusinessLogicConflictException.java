package com.jide.accountservice.infrastructure.exceptions;

public class BusinessLogicConflictException extends RuntimeException{
    public BusinessLogicConflictException(String message){
        super(message);
    }
}
