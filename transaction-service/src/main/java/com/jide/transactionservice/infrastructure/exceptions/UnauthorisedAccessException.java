package com.jide.transactionservice.infrastructure.exceptions;


public class UnauthorisedAccessException extends RuntimeException {
    public UnauthorisedAccessException(String message){
        super(message);
    }
}
