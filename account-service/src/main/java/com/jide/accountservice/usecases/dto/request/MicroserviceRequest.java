package com.jide.accountservice.usecases.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MicroserviceRequest {

    private Long id;

    private String username;

    private String type;



}