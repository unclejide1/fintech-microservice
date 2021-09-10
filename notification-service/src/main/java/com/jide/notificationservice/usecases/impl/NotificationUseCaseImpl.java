package com.jide.notificationservice.usecases.impl;

import com.jide.notificationservice.domain.dao.AccountTransactionEntityDao;
import com.jide.notificationservice.domain.dao.FintechAccountEntityDao;
import com.jide.notificationservice.domain.dao.UserDao;
import com.jide.notificationservice.domain.entities.AccountTransactionEntity;
import com.jide.notificationservice.domain.entities.FintechAccountEntity;
import com.jide.notificationservice.domain.entities.User;
import com.jide.notificationservice.usecases.NotificationUseCase;
import com.jide.notificationservice.usecases.model.Mail;

import com.jide.notificationservice.usecases.model.MicroserviceRequest;
import com.jide.notificationservice.usecases.utils.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationUseCaseImpl implements NotificationUseCase {


    private final UserDao userDao;




    private final FintechAccountEntityDao fintechAccountEntityDao;

    private final AccountTransactionEntityDao accountTransactionEntityDao;


    public EmailSender emailSender;

    @Value("${gateway.public.ip:3.16.180.134}")
    private String gatewayPublicIp;

    @Autowired
    public NotificationUseCaseImpl(UserDao userDao, FintechAccountEntityDao fintechAccountEntityDao, AccountTransactionEntityDao accountTransactionEntityDao,
                                   EmailSender emailSender) {
        this.userDao = userDao;
        this.fintechAccountEntityDao = fintechAccountEntityDao;
        this.accountTransactionEntityDao = accountTransactionEntityDao;
        this.emailSender = emailSender;
    }


    @Override
    public String sendMessage(MicroserviceRequest request) {
        String link = String.format("http://%s:8765/accounts/api/v1/auth/%s",gatewayPublicIp, String.valueOf(request.getId()));

        Mail newMail = new Mail();
        newMail.setTo(request.getUsername());
        User user = userDao.getUserByEmail(request.getUsername());

        String name = String.format("%s %s", user.getFirstName(), user.getLastName());
        String message = "";
        String response = "failed";
        try {
            switch (request.getType().toUpperCase()) {

                case "VERIFY":
                    newMail.setSubject("Verify your Profile");


                    message =
                            "Hello " + name + " ,\n" +
                                    "You just created a profile on Fintech\n" +
                                    "verify your account by clicking this link\n" +
                                    link + "\n" +
                                    "You are Welcome.";

                    break;


                case "ACCOUNT-OPEN":
                    newMail.setSubject("Your Account Details");
                    FintechAccountEntity fintechAccountEntity = fintechAccountEntityDao.getRecordById(request.getId());
                    String accountNo = fintechAccountEntity.getAccountId();
                    String accountType = fintechAccountEntity.getAccountType().name();


                    message =
                            "Hello " + name + ", \n" +
                                    "You just created an account on Fintech\n" +
                                    "Here is your account details\n" +
                                    "AccountNo:  " + accountNo + "\n" +
                                    "AccountType:  " + accountType + "\n" +
                                    "You are Welcome.";
                    break;
                case "FUND":
                    newMail.setSubject("Your Account has been Funded");
                    AccountTransactionEntity accountTransactionEntity = accountTransactionEntityDao.getRecordById(request.getId());
                    String amount = accountTransactionEntity.getAmount().toString();
                    String balance = accountTransactionEntity.getCurrentBalance().toString();
                    String transactionType = accountTransactionEntity.getTransactionType().name();


                    message =
                            "Hello " + name + ", \n" +
                                    "You account on Fintech has just been funded\n" +
                                    "Here is your account details\n" +
                                    "Amount:  " + amount + "\n" +
                                    "CurrentBalance:  " + balance + "\n" +
                                    "Transaction type:  " + transactionType + "\n" +
                                    "Transaction reference:  " + accountTransactionEntity.getTransactionReference() + "\n" +
                                    "You are Welcome.";
                    break;

                case "DEBIT":
                    newMail.setSubject("DEBIT ALERT");
                    AccountTransactionEntity accountTransactionEntityDebit = accountTransactionEntityDao.getRecordById(request.getId());
                    String amountDebit = accountTransactionEntityDebit.getAmount().toString();
                    String balanceDebit = accountTransactionEntityDebit.getCurrentBalance().toString();
                    String transactionTypeDebit = accountTransactionEntityDebit.getTransactionType().name();


                    message =
                            "Hello " + name + ", \n" +
                                    "You account on Fintech has just been Debited\n" +
                                    "Here is your account details\n" +
                                    "Amount:  " + amountDebit + "\n" +
                                    "CurrentBalance:  " + balanceDebit + "\n" +
                                    "Transaction type:  " + transactionTypeDebit + "\n" +
                                    "Transaction reference:  " + accountTransactionEntityDebit.getTransactionReference() + "\n" +
                                    "You are Welcome.";
                    break;

                case "CREDIT":
                    newMail.setSubject("CREDIT ALERT");
                    AccountTransactionEntity accountTransactionEntityCredit = accountTransactionEntityDao.getRecordById(request.getId());
                    String amountCredit = accountTransactionEntityCredit.getAmount().toString();
                    String balanceCredit = accountTransactionEntityCredit.getCurrentBalance().toString();
                    String transactionTypeCredit = accountTransactionEntityCredit.getTransactionType().name();


                    message =
                            "Hello " + name + ", \n" +
                                    "You account on Fintech has just been Credited\n" +
                                    "Here is your account details\n" +
                                    "Amount:  " + amountCredit + "\n" +
                                    "CurrentBalance:  " + balanceCredit + "\n" +
                                    "Transaction type:  " + transactionTypeCredit + "\n" +
                                    "Transaction reference:  " + accountTransactionEntityCredit.getTransactionReference() + "\n" +
                                    "You are Welcome.";
                    break;


            }
            log.info("messsage>>>>>>>" + message);
            newMail.setText(message);
            emailSender.sendEmail(request.getUsername(), newMail.getSubject(), newMail.getText());
            response = "sent";
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return response;
    }

}
