package com.jide.transactionservice.usecases.impl;

import com.jide.transactionservice.domain.dao.*;
import com.jide.transactionservice.domain.entities.AccountFundingTransactionEntity;
import com.jide.transactionservice.domain.entities.AccountTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.entities.User;
import com.jide.transactionservice.domain.enums.AccountOpeningStageConstant;
import com.jide.transactionservice.domain.enums.TransactionTypeConstant;
import com.jide.transactionservice.infrastructure.exceptions.CustomException;
import com.jide.transactionservice.usecases.FundAccountUseCase;
import com.jide.transactionservice.usecases.dto.request.AccountFundingRequest;
import com.jide.transactionservice.usecases.dto.response.AccountFundingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Named
@AllArgsConstructor
public class FundAccountUseCaseImpl implements FundAccountUseCase {
    private final UserDao userDao;
    private final FintechAccountEntityDao fintechAccountEntityDao;
    private final SequenceEntityDao sequenceEntityDao;
    private final AccountFundingTransactionEntityDao fundingTransactionEntityDao;
    private final AccountTransactionEntityDao transactionEntityDao;

    private static final Logger logger = LoggerFactory.getLogger(FundAccountUseCaseImpl.class);

    @Override
    public AccountFundingResponse fundAccount(AccountFundingRequest request, String userEmail) {
        //TODO
        //1. check if the user has been verified.
        //2. get the user and figure out if the account belongs to him.
        //4. generate transaction reference.
        //5. get account and update account balance.
        //6. create a transactionEntity for this transaction.
        //7. notify user on successful transaction.
        User user = userDao.findUserByEmail(userEmail).orElseThrow(() ->  new CustomException("Error: User is not found.", HttpStatus.NOT_FOUND));
        if(!user.getStatus().name().equalsIgnoreCase(AccountOpeningStageConstant.VERIFIED.name())){
            throw new CustomException("Please verify your profile " , HttpStatus.BAD_REQUEST);
        }
        FintechAccountEntity userAccountEntity = fintechAccountEntityDao.findAccountByAccountId(request.getAccountNo())
                .orElseThrow(() ->  new CustomException("Error: Account is not found.", HttpStatus.NOT_FOUND));

        if(!userAccountEntity.getCreator().getEmail().equalsIgnoreCase(userEmail)){
            throw new CustomException("This account does not belong to you " , HttpStatus.BAD_REQUEST);
        }
        String transactionRef = sequenceEntityDao.getNextTransactionReference();
        BigDecimal oldBalance = userAccountEntity.getAvailableBalance();
        userAccountEntity.setAvailableBalance(userAccountEntity.getAvailableBalance().add(request.getAmount()));
        FintechAccountEntity updatedUserAccount =fintechAccountEntityDao.saveRecord(userAccountEntity);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        AccountFundingTransactionEntity accountFundingTransactionEntity = AccountFundingTransactionEntity.builder()
                .bankAccount(updatedUserAccount)
                .transactionReference(transactionRef)
                .amount(request.getAmount())
                .paymentDate(LocalDateTime.now())
                .build();
        AccountFundingTransactionEntity fundingTransactionEntity = fundingTransactionEntityDao.saveRecord(accountFundingTransactionEntity);
        logger.info("fundingAccountDetails>>>> "+ fundingTransactionEntity);
        AccountTransactionEntity accountTransactionEntity = AccountTransactionEntity.builder()
                .bankAccount(updatedUserAccount)
                .transactionDate(LocalDateTime.now())
                .balanceBeforeTransaction(oldBalance)
                .amount(request.getAmount())
                .currentBalance(updatedUserAccount.getAvailableBalance())
                .transactionReference(transactionRef)
                .transactionType(TransactionTypeConstant.CREDIT)
                .narration("Account funding")
                .build();

        AccountTransactionEntity savedAccountTransaction = transactionEntityDao.saveRecord(accountTransactionEntity);
        AccountFundingResponse accountFundingResponse = AccountFundingResponse.builder()
                .accountNo(updatedUserAccount.getAccountId())
                .accountType(updatedUserAccount.getAccountType().name())
                .amountFunded(request.getAmount().toString())
                .availableBalance(updatedUserAccount.getAvailableBalance().toString())
                .transactionReference(transactionRef)
                .build();

        return accountFundingResponse;

    }
}
