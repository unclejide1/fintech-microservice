package com.jide.notificationservice.usecases.impl;

import com.jide.notificationservice.domain.dao.UserDao;
import com.jide.notificationservice.usecases.NotificationUseCase;
import com.jide.notificationservice.usecases.model.Mail;
import com.jide.notificationservice.usecases.model.User;
import com.jide.notificationservice.usecases.utils.EmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

@Slf4j
@Named
@AllArgsConstructor
public class NotificationUseCaseImpl implements NotificationUseCase {

    private final UserDao userDao;

    @Autowired
    public EmailSender emailSender;


    @Override
    public String sendMessage(User input) {
        String link = String.format("http://localhost:8765/accounts/api/v1/auth/%s", String.valueOf(input.getId()));

        Mail newMail = new Mail();
        newMail.setTo(input.getUsername());
        newMail.setSubject("TestSubject");
        newMail.setText("TestText");

        String message =
                "Hello" + input.getUsername() + ",\n" +
                        "You just created an account on QueuePay\n" +
                        "verify your account by clicking this link\n" +
                         link +"\n" +
                        "You are Welcome.";

        emailSender.sendEmail(input.getUsername(), "welcome to my platform", message);

        return "sent";
    }

}
