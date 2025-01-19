package com.yassir.budgetbuddy.goal.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalRequest(
        Integer id, // Optional, null when creating a new goal
        @NotNull(message = "Goal name cannot be null")
        @NotEmpty(message = "Goal name cannot be empty")
        String name,

        String description, // Optional description of the goal

        @NotNull(message = "Target amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Target amount must be greater than 0")
        BigDecimal targetAmount,

        @NotNull(message = "Current amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Current amount must be greater than 0")
        BigDecimal currentAmount,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "Target date is required")
        LocalDate targetDate,

        boolean reached

) {
}