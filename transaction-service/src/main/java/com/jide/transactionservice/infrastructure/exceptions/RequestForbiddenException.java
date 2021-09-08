package com.jide.transactionservice.infrastructure.exceptions;


public class RequestForbiddenException extends RuntimeException {
    public RequestForbiddenException(String message){
        super(message);
    }
}
