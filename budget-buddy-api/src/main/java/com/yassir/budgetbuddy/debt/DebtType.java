package com.yassir.budgetbuddy.debt;

import lombok.Getter;

@Getter
public enum DebtType {
    PERSONAL_LOAN("Personal Loan"),
    MORTGAGE("Mortgage"),
    CREDIT_CARD("Credit Card"),
    STUDENT_LOAN("Student Loan"),
    BUSINESS_LOAN("Business Loan"),
    CAR_LOAN("Car Loan"),
    OTHER("Other"),
    ;

    private final String type;

    DebtType(String type) {
        this.type = type;
    }
}
