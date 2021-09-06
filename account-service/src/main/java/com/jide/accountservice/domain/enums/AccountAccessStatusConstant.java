package com.jide.accountservice.domain.enums;

public enum AccountAccessStatusConstant {
    ACTIVE,
    BLOCKED_TEMPORARILY,  // A DATE IS SET FOR IT TO BE ACTIVE
    BLOCKED_INDEFINITELY, // THE CUSTOMER HAS TO REQUEST FOR IT TO ACTIVATED
}
