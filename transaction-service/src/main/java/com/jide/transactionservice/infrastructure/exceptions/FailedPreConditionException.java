package com.jide.transactionservice.infrastructure.exceptions;


public class FailedPreConditionException extends RuntimeException  {
    public FailedPreConditionException(String message){
        super(message);
    }
}
