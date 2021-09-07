package com.jide.transactionservice.domain.dao;


import com.jide.transactionservice.domain.enums.SequenceType;

public interface SequenceEntityDao {
    String getNextAccountId();
    String getNextSequenceId(SequenceType sequenceType);
    String getNextTransactionReference();
}
