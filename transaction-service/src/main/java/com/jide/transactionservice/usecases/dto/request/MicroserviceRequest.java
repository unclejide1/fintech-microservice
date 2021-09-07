package com.jide.transactionservice.usecases.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MicroserviceRequest {

    private Long id;

    private String username;

    private String type;



}