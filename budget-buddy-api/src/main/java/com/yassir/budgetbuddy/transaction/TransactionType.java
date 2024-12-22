package com.yassir.budgetbuddy.transaction;

public enum TransactionType {
    DEPOSIT,           // For adding money to the wallet
    WITHDRAWAL,        // For removing money from the wallet
    TRANSFER,          // For transferring money between wallets
    TRANSFER_TO_GOAL   // For transferring money from a wallet to a goal
}
