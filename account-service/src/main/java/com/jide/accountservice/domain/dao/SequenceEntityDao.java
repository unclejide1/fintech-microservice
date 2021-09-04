package com.jide.accountservice.domain.dao;


import com.jide.accountservice.domain.enums.SequenceType;

public interface SequenceEntityDao {
    String getNextAccountId();
    String getNextSequenceId(SequenceType sequenceType);
    String getNextTransactionReference();
}
