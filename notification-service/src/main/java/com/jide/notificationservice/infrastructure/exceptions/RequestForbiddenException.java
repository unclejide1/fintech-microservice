package com.jide.notificationservice.infrastructure.exceptions;


public class RequestForbiddenException extends RuntimeException {
    public RequestForbiddenException(String message){
        super(message);
    }
}
