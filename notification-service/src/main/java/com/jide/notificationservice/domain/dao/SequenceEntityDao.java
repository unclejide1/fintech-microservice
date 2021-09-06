package com.jide.notificationservice.domain.dao;


import com.jide.notificationservice.domain.enums.SequenceType;

public interface SequenceEntityDao {
    String getNextAccountId();
    String getNextSequenceId(SequenceType sequenceType);
    String getNextTransactionReference();
}
