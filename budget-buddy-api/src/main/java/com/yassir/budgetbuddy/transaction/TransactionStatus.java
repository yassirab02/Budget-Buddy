package com.yassir.budgetbuddy.transaction;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),

    ;

    private final String status;

    TransactionStatus(String status) {
        this.status = status;
    }
}
