package com.jide.transactionservice.usecases.impl;

import com.jide.transactionservice.domain.dao.*;
import com.jide.transactionservice.domain.entities.AccountFundingTransactionEntity;
import com.jide.transactionservice.domain.entities.AccountTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.entities.User;
import com.jide.transactionservice.domain.enums.AccountOpeningStageConstant;
import com.jide.transactionservice.domain.enums.AccountTypeConstant;
import com.jide.transactionservice.domain.enums.GenderTypeConstant;
import com.jide.transactionservice.infrastructure.configurations.NotificationFeignClient;
import com.jide.transactionservice.usecases.dto.request.AccountFundingRequest;
import com.jide.transactionservice.usecases.dto.request.MicroserviceRequest;
import com.jide.transactionservice.usecases.dto.response.AccountFundingResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FundAccountUseCaseImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private FintechAccountEntityDao fintechAccountEntityDao;

    @Mock
    private NotificationFeignClient notificationFeignClient;

    @Mock
    private SequenceEntityDao sequenceEntityDao;
    @Mock
    private  AccountFundingTransactionEntityDao fundingTransactionEntityDao;
    @Mock
    private  AccountTransactionEntityDao transactionEntityDao;

    @InjectMocks
    private FundAccountUseCaseImpl fundAccountUseCase;

    private static User user;

    private static User userBad;

    private static FintechAccountEntity fintechAccountEntity;
    private static FintechAccountEntity fintechAccountEntitysec;
    private static AccountTransactionEntity accountTransactionEntity;
    private static AccountTransactionEntity accountTransactionEntitySec;
    private static AccountFundingTransactionEntity accountFundingTransactionEntity;


    private static MicroserviceRequest microserviceRequest;
    private static AccountFundingRequest accountFundingRequest;
    private static AccountFundingResponse accountFundingResponse;
    private static String accountId;
    private static String transactionRef;

//    private static CreateAccountRequest createAccountRequest;
//
//    private static AccountCreationResponse accountCreationResponse;


    @BeforeAll
    public static void init() {


        user = new User();
        user.setId(1L);
        user.setFirstName("john");
        user.setLastName("doe");
        user.setGender(GenderTypeConstant.MALE);
        user.setPhoneNumber("0813705817");
        user.setEmail("j@g.com");
        user.setStatus(AccountOpeningStageConstant.VERIFIED);
        user.setPassword("password");

        userBad = new User();
        userBad.setId(1L);
        userBad.setFirstName("john");
        userBad.setLastName("doe");
        userBad.setGender(GenderTypeConstant.MALE);
        userBad.setPhoneNumber("0813705817");
        userBad.setEmail("j@gg.com");
        userBad.setStatus(AccountOpeningStageConstant.VERIFIED);
        userBad.setPassword("password");

        accountId = "1234567";

        transactionRef ="0000000";


        microserviceRequest = new MicroserviceRequest();
        microserviceRequest.setId(user.getId());
        microserviceRequest.setType("ACCOUNT-OPEN");
        microserviceRequest.setUsername(user.getEmail());


        fintechAccountEntity = new FintechAccountEntity();
        fintechAccountEntity.setId(8L);
        fintechAccountEntity.setAccountId(accountId);
        fintechAccountEntity.setAccountType(AccountTypeConstant.SAVINGS);
        fintechAccountEntity.setCreator(user);
        fintechAccountEntity.setDailyTransactionLimit(BigDecimal.ZERO);
        fintechAccountEntity.setAvailableBalance(BigDecimal.ZERO);

        fintechAccountEntitysec = new FintechAccountEntity();
        fintechAccountEntitysec.setId(8L);
        fintechAccountEntitysec.setAccountId(accountId);
        fintechAccountEntitysec.setAccountType(AccountTypeConstant.SAVINGS);
        fintechAccountEntitysec.setCreator(user);
        fintechAccountEntitysec.setDailyTransactionLimit(BigDecimal.ZERO);
        fintechAccountEntitysec.setAvailableBalance(BigDecimal.TEN);

        accountFundingRequest = new AccountFundingRequest();
        accountFundingRequest.setAccountNo(accountId);
        accountFundingRequest.setAmount(BigDecimal.TEN);

        accountFundingTransactionEntity = new AccountFundingTransactionEntity();
//        accountTransactionEntity.setId(2L);
        accountFundingTransactionEntity.setAmount(accountFundingRequest.getAmount());
        accountFundingTransactionEntity.setBankAccount(fintechAccountEntity);
        accountFundingTransactionEntity.setPaymentDate(LocalDateTime.now());
        accountFundingTransactionEntity.setTransactionReference(transactionRef);

        accountTransactionEntity = new AccountTransactionEntity();
//        accountTransactionEntity.setId(5L);
        accountTransactionEntity.setBankAccount(fintechAccountEntity);
        accountTransactionEntity.setBalanceBeforeTransaction(BigDecimal.ZERO);
        accountTransactionEntity.setAmount(accountFundingRequest.getAmount());
        accountTransactionEntity.setTransactionDate(LocalDateTime.now());
        accountTransactionEntity.setCurrentBalance(fintechAccountEntitysec.getAvailableBalance());

        accountTransactionEntitySec = new AccountTransactionEntity();
        accountTransactionEntitySec.setId(5L);
        accountTransactionEntitySec.setBankAccount(fintechAccountEntitysec);
        accountTransactionEntitySec.setBalanceBeforeTransaction(BigDecimal.ZERO);
        accountTransactionEntitySec.setAmount(accountFundingRequest.getAmount());
        accountTransactionEntitySec.setTransactionDate(LocalDateTime.now());
        accountTransactionEntitySec.setCurrentBalance(fintechAccountEntitysec.getAvailableBalance());


        microserviceRequest = new MicroserviceRequest();
        microserviceRequest.setId(accountTransactionEntity.getId());
        microserviceRequest.setType("FUND");
        microserviceRequest.setUsername(user.getEmail());

    }

    @BeforeEach
    void setMockOutput() throws Exception {

        Mockito.when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(fintechAccountEntityDao.generateAccountId()).thenReturn(accountId);
        Mockito.when(sequenceEntityDao.getNextTransactionReference()).thenReturn(transactionRef);
        Mockito.when(fintechAccountEntityDao.saveRecord(fintechAccountEntity)).thenReturn(fintechAccountEntity);


        Mockito.when(fintechAccountEntityDao.findAccountByAccountId(accountId)).thenReturn(Optional.of(fintechAccountEntity));
        Mockito.when(fundingTransactionEntityDao.saveRecord(accountFundingTransactionEntity)).thenReturn(accountFundingTransactionEntity);
        Mockito.when(transactionEntityDao.saveRecord(accountTransactionEntity)).thenReturn(accountTransactionEntitySec);
        Mockito.when(notificationFeignClient.notify(microserviceRequest)).thenReturn("sent");




    }


    @Test
    public void validateThatMethodDoesNotThrowAnExceptionWithTheRightInput() {
        assertDoesNotThrow(() ->fundAccountUseCase.fundAccount(accountFundingRequest, user.getEmail()));
    }
}