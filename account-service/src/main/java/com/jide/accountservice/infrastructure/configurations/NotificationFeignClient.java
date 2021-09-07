package com.jide.accountservice.infrastructure.configurations;

import com.jide.accountservice.usecases.dto.request.MicroserviceRequest;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service")
public interface NotificationFeignClient {

    @PostMapping("/api/v1/notify/send/")
    @Headers("Content-Type: application/json")
    public String notify(MicroserviceRequest request);
}
