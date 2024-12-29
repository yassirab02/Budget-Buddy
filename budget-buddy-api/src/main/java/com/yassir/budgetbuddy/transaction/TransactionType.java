package com.yassir.budgetbuddy.transaction;

import lombok.Getter;

@Getter
public enum TransactionType {
    TRANSFER("Transfer"),
    TRANSFER_TO_GOAL("Transfer to Goal"),
    TRANSFER_TO_WALLET("Transfer to Wallet");

    private final String typeName;

    TransactionType(String typeName) {
        this.typeName = typeName;
    }

}

