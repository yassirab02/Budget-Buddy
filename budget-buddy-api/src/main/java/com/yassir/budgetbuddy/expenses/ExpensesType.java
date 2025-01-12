package com.yassir.budgetbuddy.expenses;

import lombok.Getter;

@Getter
public enum ExpensesType {
    FIXED("Fixed"),
    VARIABLE("Variable"),
    ONE_TIME("One Time");

    private final String type;

    ExpensesType(String type) {
        this.type = type;
    }

}

