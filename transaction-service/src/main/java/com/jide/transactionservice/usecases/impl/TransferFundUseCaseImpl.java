package com.jide.transactionservice.usecases.impl;

import com.google.gson.Gson;
import com.jide.transactionservice.domain.dao.*;
import com.jide.transactionservice.domain.entities.AccountTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.entities.User;
import com.jide.transactionservice.domain.enums.AccountOpeningStageConstant;
import com.jide.transactionservice.domain.enums.TransactionTypeConstant;
import com.jide.transactionservice.infrastructure.configurations.NotificationFeignClient;
import com.jide.transactionservice.infrastructure.exceptions.CustomException;
import com.jide.transactionservice.usecases.TransferFundUseCase;
import com.jide.transactionservice.usecases.dto.request.MicroserviceRequest;
import com.jide.transactionservice.usecases.dto.request.TransferFundRequest;
import com.jide.transactionservice.usecases.dto.response.TransferFundResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Named
@AllArgsConstructor
public class TransferFundUseCaseImpl implements TransferFundUseCase {


    private final UserDao userDao;
    private final FintechAccountEntityDao fintechAccountEntityDao;
    private final SequenceEntityDao sequenceEntityDao;
    private final AccountFundingTransactionEntityDao fundingTransactionEntityDao;
    private final AccountTransactionEntityDao transactionEntityDao;
    private final NotificationFeignClient notificationFeignClient;

    private static final Logger logger = LoggerFactory.getLogger(FundAccountUseCaseImpl.class);

//    Gson gson = new Gson();

    @Override
    public TransferFundResponse sendMoney(TransferFundRequest transferFundRequest, String userEmail) {

        //TODO
        //1. check if the user has been verified.
        //2. get the user and figure out if the account belongs to him.
        //3. get the receiving account.
        //4. generate transaction reference.
        //5. get account and update account balance for the sender.
        //5. get account and update account balance for the receiver.
        //6. create a transactionEntity for this transaction for the sender.
        //6. create a transactionEntity for this transaction for the receiver.
        //7. notify the sender and receiver on successful transaction.
        logger.info("transfer fund request>> " + transferFundRequest.toString() + " " + userEmail);
        User user = userDao.findUserByEmail(userEmail).orElseThrow(() ->  new CustomException("Error: User is not found.", HttpStatus.NOT_FOUND));
        if(!user.getStatus().name().equalsIgnoreCase(AccountOpeningStageConstant.VERIFIED.name())){
            throw new CustomException("Please verify your profile " , HttpStatus.BAD_REQUEST);
        }
        FintechAccountEntity senderAccountEntity = fintechAccountEntityDao.findAccountByAccountId(transferFundRequest.getFundingAccount())
                .orElseThrow(() ->  new CustomException("Error:Funding Account not found.", HttpStatus.NOT_FOUND));

        logger.info("funding account details>> " + senderAccountEntity.toString() );
        if(!senderAccountEntity.getCreator().getEmail().equalsIgnoreCase(userEmail)){
            throw new CustomException("This account does not belong to you " , HttpStatus.BAD_REQUEST);
        }

        if(senderAccountEntity.getAvailableBalance().compareTo(transferFundRequest.getAmount()) <= 0){
            throw new CustomException("Your balance is not enough to perform this transaction " , HttpStatus.BAD_REQUEST);
        }

        FintechAccountEntity receivingAccountEntity = fintechAccountEntityDao.findAccountByAccountId(transferFundRequest.getReceivingAccount())
                .orElseThrow(() ->  new CustomException("Error:Funding Account not found.", HttpStatus.NOT_FOUND));

        User receiver = receivingAccountEntity.getCreator();

        logger.info("receiving account details>> " + receivingAccountEntity.toString() );

        String transactionRef = sequenceEntityDao.getNextTransactionReference();
        BigDecimal senderOldBalance = senderAccountEntity.getAvailableBalance();
        logger.info("old sender account balance>> " + senderOldBalance.toString());
        BigDecimal receiverOldBalance = receivingAccountEntity.getAvailableBalance();
        logger.info("old receiver account balance>> " + receiverOldBalance.toString());

        senderAccountEntity.setAvailableBalance(senderAccountEntity.getAvailableBalance().subtract(transferFundRequest.getAmount()));
        receivingAccountEntity.setAvailableBalance(receivingAccountEntity.getAvailableBalance().add(transferFundRequest.getAmount()));

        FintechAccountEntity updatedSenderAccount = fintechAccountEntityDao.saveRecord(senderAccountEntity);
        logger.info("updated sender account details>> " + receivingAccountEntity.toString() );
        FintechAccountEntity updatedReceiverAccount = fintechAccountEntityDao.saveRecord(receivingAccountEntity);
        logger.info("updated receiver account details>> " + receivingAccountEntity.toString() );


        String narration = StringUtils.EMPTY;
        if(transferFundRequest.getNarration() != null){
            narration = transferFundRequest.getNarration();
        }

        LocalDateTime transactionDate = LocalDateTime.now();

        AccountTransactionEntity senderAccountTransactionEntity = AccountTransactionEntity.builder()
                .bankAccount(updatedSenderAccount)
                .transactionDate(transactionDate)
                .balanceBeforeTransaction(senderOldBalance)
                .amount(transferFundRequest.getAmount())
                .currentBalance(updatedSenderAccount.getAvailableBalance())
                .transactionReference(transactionRef)
                .transactionType(TransactionTypeConstant.DEBIT)
                .narration(narration)
                .build();

        logger.info("updated sender account details>> " + receivingAccountEntity.toString());

        AccountTransactionEntity receiverAccountTransactionEntity = AccountTransactionEntity.builder()
                .bankAccount(updatedReceiverAccount)
                .transactionDate(transactionDate)
                .balanceBeforeTransaction(receiverOldBalance)
                .amount(transferFundRequest.getAmount())
                .currentBalance(updatedReceiverAccount.getAvailableBalance())
                .transactionReference(transactionRef)
                .transactionType(TransactionTypeConstant.CREDIT)
                .narration(narration)
                .build();

        logger.info("updated receiver account details>> " + receivingAccountEntity.toString());

        AccountTransactionEntity savedSenderAccountTransaction = transactionEntityDao.saveRecord(senderAccountTransactionEntity);
        AccountTransactionEntity savedReceiverAccountTransaction = transactionEntityDao.saveRecord(receiverAccountTransactionEntity);

        MicroserviceRequest microserviceRequestSender = MicroserviceRequest.builder()
                .id(savedSenderAccountTransaction.getId())
                .username(user.getEmail())
                .type("DEBIT")
                .build();

        MicroserviceRequest microserviceRequestReceiver = MicroserviceRequest.builder()
                .id(savedReceiverAccountTransaction.getId())
                .username(receiver.getEmail())
                .type("CREDIT")
                .build();

        String responseSender = "";
        String responseReceiver = "";
        logger.info("about to call notification service");
        try {
            responseSender = notificationFeignClient.notify(microserviceRequestSender);
            responseReceiver = notificationFeignClient.notify(microserviceRequestReceiver);
        }catch (FeignException.FeignClientException e){
            logger.error(e.getMessage());
        }
        logger.info("called notification service with response "+ responseSender);
        logger.info("called notification service with response "+ responseReceiver);


        TransferFundResponse transferFundResponse = TransferFundResponse.builder()
                .accountNumber(transferFundRequest.getFundingAccount())
                .amountSent(transferFundRequest.getAmount().toString())
                .availableBalance(updatedSenderAccount.getAvailableBalance().toString())
                .build();

        //TODO
        // send notification
        return transferFundResponse;
    }
}
