package com.jide.accountservice.infrastructure.exceptions;


public class FailedPreConditionException extends RuntimeException  {
    public FailedPreConditionException(String message){
        super(message);
    }
}
