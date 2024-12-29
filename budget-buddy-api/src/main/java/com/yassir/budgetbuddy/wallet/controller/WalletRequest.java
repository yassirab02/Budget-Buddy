package com.yassir.budgetbuddy.wallet.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WalletRequest(
        Integer id, // Optional if updating, should be null for new wallets

        @NotNull(message = "100")
        String name,

        @NotNull(message = "102")
        @DecimalMin(value = "0.0", inclusive = false, message = "Balance must be greater than 0")
        BigDecimal balance,

        BigDecimal totalIncome,

        BigDecimal totalExpenses,

        Integer currencyTypeId // ID of the currency type (e.g., USD, EUR)
) {
}
