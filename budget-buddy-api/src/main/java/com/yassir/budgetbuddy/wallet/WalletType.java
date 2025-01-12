package com.yassir.budgetbuddy.wallet;

import lombok.Getter;

@Getter
public enum WalletType {
    SPENDING("Spending"),
    SAVINGS("Savings"),
    CASH("Cash"),
    ;

    private final String name;

    WalletType(String name) {
        this.name = name;
    }
}
