package com.jide.transactionservice.infrastructure.persistence.daoImpl;


import com.jide.transactionservice.domain.dao.AccountTransactionEntityDao;
import com.jide.transactionservice.domain.entities.AccountTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.enums.TransactionTypeConstant;
import com.jide.transactionservice.infrastructure.persistence.repository.AccountTransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Named
public class AccountTransactionEntityDaoImpl extends CrudDaoImpl<AccountTransactionEntity, Long> implements AccountTransactionEntityDao {

    private final AccountTransactionRepository repository;
    public AccountTransactionEntityDaoImpl(AccountTransactionRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Optional<AccountTransactionEntity> findByAccountAndReference(FintechAccountEntity bankAccountEntity, String reference) {
        return repository.findFirstByBankAccountAndTransactionReference(bankAccountEntity, reference);
    }

    @Override
    public List<AccountTransactionEntity> getTransactionsOnAccountWithinPeriod(FintechAccountEntity bankAccountEntity, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getAllByBankAccountAndTransactionDateBetweenOrderByTransactionDateDesc(bankAccountEntity, startDate, endDate);
    }

    @Override
    public List<AccountTransactionEntity> getTransactionsOnAccountWithinPeriodByTransactionType(FintechAccountEntity bankAccountEntity, TransactionTypeConstant transactionTypeConstant, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.getAllByBankAccountAndTransactionTypeAndTransactionDateBetweenOrderByTransactionDateDesc(bankAccountEntity, transactionTypeConstant, startDate, endDate);
//        return null;
    }

    @Override
    public List<AccountTransactionEntity> getTransactionsOnAccount(FintechAccountEntity bankAccountEntity, int size) {
        Pageable pageable = PageRequest.of(0, size);
        return repository.getAllByBankAccountOrderByTransactionDateDesc(bankAccountEntity, pageable);
    }
}
