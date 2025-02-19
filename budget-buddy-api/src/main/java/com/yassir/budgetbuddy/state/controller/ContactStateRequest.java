package com.yassir.budgetbuddy.state.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ContactStateRequest(
        Integer id,
        @NotNull(message = "Name cannot be null")
        @NotEmpty(message = "Name cannot be empty")
        String name,
        @NotNull(message = "Code cannot be null")
        @NotEmpty(message = "Code cannot be empty")
        String code,
        String description
        ) {
}
