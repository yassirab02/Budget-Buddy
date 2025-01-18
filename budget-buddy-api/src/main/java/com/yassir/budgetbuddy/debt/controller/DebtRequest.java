package com.yassir.budgetbuddy.debt.controller;


import com.yassir.budgetbuddy.debt.DebtType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DebtRequest(
        Integer id, // Optional, null for new debts

        @NotNull(message = "Debt name cannot be null")
        @NotEmpty(message = "Debt name cannot be empty")
        String name,

        String description,

        @NotNull(message = "Debt amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount,

        DebtType type,

        LocalDate dueDate,

        LocalDate issueDate,

        boolean isPaid
) {
}