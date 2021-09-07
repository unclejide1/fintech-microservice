package com.jide.transactionservice.infrastructure.exceptions;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
