package com.yassir.budgetbuddy.income.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeRequest(

        Integer id, // Optional, null when creating a new income entry
        @NotNull(message = "Income name cannot be null")
        @NotEmpty(message = "Income name cannot be empty")
        String name,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount,

        String description,

        LocalDate date,

        @NotNull(message = "Income source ID is required")
        Integer incomeSourceId,

        @NotNull(message = "Wallet ID is required")
        Integer walletId
) {
}
