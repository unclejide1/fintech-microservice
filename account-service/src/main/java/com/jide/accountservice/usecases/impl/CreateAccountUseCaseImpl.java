package com.jide.accountservice.usecases.impl;

import com.google.gson.Gson;
import com.jide.accountservice.domain.dao.FintechAccountEntityDao;
import com.jide.accountservice.domain.dao.RoleDao;
import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.FintechAccountEntity;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.AccountOpeningStageConstant;
import com.jide.accountservice.domain.enums.AccountTypeConstant;
import com.jide.accountservice.infrastructure.configurations.NotificationFeignClient;
import com.jide.accountservice.infrastructure.exceptions.CustomException;
import com.jide.accountservice.usecases.CreateAccountUseCase;
import com.jide.accountservice.usecases.auth.impl.SignUpUseCaseImpl;
import com.jide.accountservice.usecases.dto.request.CreateAccountRequest;
import com.jide.accountservice.usecases.dto.request.MicroserviceRequest;
import com.jide.accountservice.usecases.dto.response.AccountCreationResponse;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Optional;


@Slf4j
@Named
@AllArgsConstructor
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final UserDao userDao;
    private FintechAccountEntityDao fintechAccountEntityDao;
    private final NotificationFeignClient notificationFeignClient;
    private final RestTemplate restTemplate;


    private static final Logger logger = LoggerFactory.getLogger(CreateAccountUseCaseImpl.class);

    @Override
    public AccountCreationResponse createAccount(CreateAccountRequest createAccountRequest , String userEmail) {
        User  user = userDao.findUserByEmail(userEmail).orElseThrow(() ->  new CustomException("Error: User is not found.", HttpStatus.NOT_FOUND));
        if(!user.getStatus().name().equalsIgnoreCase(AccountOpeningStageConstant.VERIFIED.name())){
            throw new CustomException("Please verify your profile " , HttpStatus.BAD_REQUEST);
        }
        BigDecimal limit = createAccountRequest.getLimit();

        if (limit.compareTo(BigDecimal.ZERO) <= 0){
            limit = BigDecimal.ZERO;
        }

        String accountNo = fintechAccountEntityDao.generateAccountId();
        FintechAccountEntity fintechAccountEntity = FintechAccountEntity.builder()
                .accountId(accountNo)
                .accountType(AccountTypeConstant.valueOf(createAccountRequest.getAccountType().toUpperCase()))
                .creator(user)
                .dailyTransactionLimit(limit)
                .build();
        FintechAccountEntity fintechAccountEntityNew = fintechAccountEntityDao.saveRecord(fintechAccountEntity);
        MicroserviceRequest microserviceRequest = MicroserviceRequest.builder()
                .id(fintechAccountEntityNew.getId())
                .username(user.getEmail())
                .type("ACCOUNT-OPEN")
                .build();

        String response = "";


        logger.info("about to call notification service to notify on successful funding");
        try {
            response = notificationFeignClient.notify(microserviceRequest);
        }catch (FeignException.FeignClientException e){
            logger.error(e.getMessage());
        }
        logger.info("called notification service with response "+ response);


        logger.info("user>>>" + fintechAccountEntityNew.toString());
        System.out.println(fintechAccountEntity.toString());

        return AccountCreationResponse.builder()
                .accountNo(fintechAccountEntityNew.getAccountId())
                .availableBalance(fintechAccountEntityNew.getAvailableBalance().toString())
                .accountType(fintechAccountEntityNew.getAccountType().name())
                .limit(fintechAccountEntityNew.getDailyTransactionLimit().toString())
                .build();
    }
}
