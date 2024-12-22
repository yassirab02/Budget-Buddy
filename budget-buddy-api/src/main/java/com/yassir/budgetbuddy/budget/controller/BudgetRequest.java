package com.yassir.budgetbuddy.budget.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BudgetRequest(
        Integer id, // Optional, null when creating a new budget
        @NotNull(message = "Budget name cannot be null")
        @NotEmpty(message = "Budget name cannot be empty")
        String name,

        @NotNull(message = "Description is required")
        @NotEmpty(message = "Description cannot be empty")
        String description,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        Double amount,

        @NotNull(message = "Target amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Target amount must be greater than 0")
        Double targetAmount,

        @NotNull(message = "Limit amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Limit amount must be greater than 0")
        Double limitAmount,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate,

        String budgetCover, // Optional field for budget cover image

        @NotNull(message = "Owner ID is required")
        Integer ownerId
) {
}
