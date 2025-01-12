package com.yassir.budgetbuddy.expenses.controller;

import com.yassir.budgetbuddy.expenses.ExpensesType;
import com.yassir.budgetbuddy.transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpensesRequest(
        Integer id, // Optional, null when creating a new expense
        @NotNull(message = "Expense name cannot be null")
        @NotEmpty(message = "Expense name cannot be empty")
        String name,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount,

        String description,// Optional field

        LocalDate date,

        @NotNull(message = "Expenses Type is required")
        ExpensesType expensesType, // E.g.,Fixed, Variable

        @NotNull(message = "Category is required")
        Integer categoryId,

        @NotNull(message = "Budget ID is required")
        Integer budgetId,

        Integer walletId

) {
}
