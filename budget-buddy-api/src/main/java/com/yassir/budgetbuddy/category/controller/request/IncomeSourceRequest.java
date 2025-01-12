package com.yassir.budgetbuddy.category.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IncomeSourceRequest(
        Integer id, // Optional, null when creating a new income source
        @NotNull(message = "Income source name cannot be null")
        @NotEmpty(message = "Income source name cannot be empty")
        String name,

        String description, // Optional field

        @NotNull(message = "Icon URL is required")
        String iconUrl // URL or file path for the icon
) {
}
