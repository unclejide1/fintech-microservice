package com.jide.transactionservice.usecases.impl;

import com.google.gson.Gson;
import com.jide.transactionservice.domain.dao.*;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.entities.User;
import com.jide.transactionservice.domain.enums.AccountOpeningStageConstant;
import com.jide.transactionservice.infrastructure.exceptions.CustomException;
import com.jide.transactionservice.usecases.AccountBalanceUseCase;
import com.jide.transactionservice.usecases.dto.request.MicroserviceRequest;
import com.jide.transactionservice.usecases.dto.response.AccountBalanceResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.inject.Named;

@Slf4j
@Named
@AllArgsConstructor
public class AccountBalanceUseCaseImpl implements AccountBalanceUseCase {


    private final UserDao userDao;
    private final FintechAccountEntityDao fintechAccountEntityDao;

    private final SequenceEntityDao sequenceEntityDao;
    private final AccountFundingTransactionEntityDao fundingTransactionEntityDao;
    private final AccountTransactionEntityDao transactionEntityDao;

    private static final Logger logger = LoggerFactory.getLogger(AccountBalanceUseCaseImpl.class);

    Gson gson = new Gson();

    @Override
    public AccountBalanceResponse getAccountBalance(String accountNo, String userEmail) {

        logger.info("transfer fund request>> " + accountNo);
        User user = userDao.findUserByEmail(userEmail).orElseThrow(() ->  new CustomException("Error: User is not found.", HttpStatus.NOT_FOUND));
        if(!user.getStatus().name().equalsIgnoreCase(AccountOpeningStageConstant.VERIFIED.name())){
            throw new CustomException("Please verify your profile " , HttpStatus.BAD_REQUEST);
        }
        FintechAccountEntity senderAccountEntity = fintechAccountEntityDao.findAccountByAccountId(accountNo)
                .orElseThrow(() ->  new CustomException("Error:Funding Account not found.", HttpStatus.NOT_FOUND));

        logger.info("user's account details>> " + gson.toJson(senderAccountEntity) );
        if(!senderAccountEntity.getCreator().getEmail().equalsIgnoreCase(userEmail)){
            throw new CustomException("This account does not belong to you " , HttpStatus.BAD_REQUEST);
        }




        AccountBalanceResponse accountBalanceResponse = AccountBalanceResponse.builder()
                .accountBalance(senderAccountEntity.getAvailableBalance().toString())
                .accountNo(senderAccountEntity.getAccountId())
                .accountType(senderAccountEntity.getAccountType().name())
                .build();
        return accountBalanceResponse;
    }
}
