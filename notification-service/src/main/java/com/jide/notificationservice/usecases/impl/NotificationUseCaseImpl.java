package com.jide.notificationservice.usecases.impl;

import com.jide.notificationservice.domain.dao.FintechAccountEntityDao;
import com.jide.notificationservice.domain.dao.UserDao;
import com.jide.notificationservice.domain.entities.FintechAccountEntity;
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

    private final FintechAccountEntityDao fintechAccountEntityDao;

    @Autowired
    public EmailSender emailSender;


    @Override
    public String sendMessage(User input) {
        String link = String.format("http://localhost:8765/accounts/api/v1/auth/%s", String.valueOf(input.getId()));

        Mail newMail = new Mail();
        newMail.setTo(input.getUsername());
        String message = "";
        String response = "failed";
        try {
            switch (input.getType().toUpperCase()) {

                case "VERIFY":
                    newMail.setSubject("Verify your Profile");


                    message =
                            "Hello" + input.getUsername() + ",\n" +
                                    "You just created a profile on Fintech\n" +
                                    "verify your account by clicking this link\n" +
                                    link + "\n" +
                                    "You are Welcome.";

                    break;


                case "ACCOUNT-OPEN":
                    newMail.setSubject("Your Account Details");
                    FintechAccountEntity fintechAccountEntity = fintechAccountEntityDao.getRecordById(input.getId());
                    String accountNo = fintechAccountEntity.getAccountId();
                    String accountType = fintechAccountEntity.getAccountType().name();


                    message =
                            "Hello" + input.getUsername() + ",\n" +
                                    "You just created an account on Fintech\n" +
                                    "Here is your account details\n" +
                                    "AccountNo:  " + accountNo + "\n" +
                                    "AccountType:  " + accountType + "\n" +
                                    "You are Welcome.";
                    break;

            }
            newMail.setText(message);
            emailSender.sendEmail(input.getUsername(), newMail.getSubject(), newMail.getText());
            response = "sent";
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return response;
    }

}
