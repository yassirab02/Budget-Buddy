package com.yassir.budgetbuddy.transaction;

import lombok.Getter;

@Getter
public enum TransactionType {
    TRANSFER_TO_USER("Transfer to User"),
    TRANSFER_TO_GOAL("Transfer to Goal"),
    TRANSFER_TO_DEBT("Transfer to Debt"),
    TRANSFER_TO_WALLET("Transfer to Wallet");

    private final String typeName;

    TransactionType(String typeName) {
        this.typeName = typeName;
    }

}

