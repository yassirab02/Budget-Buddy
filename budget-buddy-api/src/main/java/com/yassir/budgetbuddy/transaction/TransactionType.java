package com.yassir.budgetbuddy.transaction;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("Deposit"),           // For adding money to the wallet
    WITHDRAWAL("Withdrawal"),        // For removing money from the wallet
    TRANSFER("Transfer"),          // For transferring money between wallets
    TRANSFER_TO_GOAL("Transfer to a goal"),   // For transferring money from a wallet to a goal
    TRANSFER_TO_WALLET("Transfer to a wallet"),// For transferring money from a wallet to a wallet
    ;

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }
}
