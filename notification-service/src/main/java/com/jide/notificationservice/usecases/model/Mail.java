package com.jide.notificationservice.usecases.model;

import lombok.Data;

@Data
public class Mail {
    private String to;
    private String subject;
    private String text;
}
