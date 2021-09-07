package com.jide.notificationservice.usecases;

import com.jide.notificationservice.usecases.model.User;

public interface NotificationUseCase {
    String sendMessage(User input);
}
