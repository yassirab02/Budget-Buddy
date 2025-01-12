package com.yassir.budgetbuddy.category.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ExpensesCategoryRequest(
        Integer id, // Optional, null when creating a new category
        @NotNull(message = "Category name cannot be null")
        @NotEmpty(message = "Category name cannot be empty")
        String name,

        String description, // Optional field

        @NotNull(message = "Icon URL is required")
        String iconUrl // URL or file path for the category icon

) {
}
