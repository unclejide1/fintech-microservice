package com.jide.notificationservice.usecases;

import com.jide.notificationservice.usecases.model.MicroserviceRequest;

public interface NotificationUseCase {
    String sendMessage(MicroserviceRequest request);
}
