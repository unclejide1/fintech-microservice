package com.jide.accountservice.infrastructure.configurations;

import com.jide.accountservice.usecases.dto.request.MicroserviceRequest;
import feign.FeignException;
import feign.Headers;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification-service", fallbackFactory = NotificationFallbackFactory.class)
public interface NotificationFeignClient {

    @PostMapping("/api/v1/notify/send/")
    @Headers("Content-Type: application/json")
    String notify(MicroserviceRequest request);
}

@Component
class NotificationFallbackFactory implements FallbackFactory<NotificationFeignClient> {

    @Override
    public NotificationFeignClient create(Throwable cause) {
        // TODO Auto-generated method stub
        return new NotificationFeignClientFallback(cause);
    }

}


class NotificationFeignClientFallback implements NotificationFeignClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Throwable cause;

    public NotificationFeignClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public String notify(MicroserviceRequest request) {
        // TODO Auto-generated method stub

        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            logger.error("404 error took place when calling notification service"
                    + cause.getLocalizedMessage());
        } else {
            logger.error("Other error took place: " + cause.getLocalizedMessage());
        }

        return "notification service is currently down";
    }

}


