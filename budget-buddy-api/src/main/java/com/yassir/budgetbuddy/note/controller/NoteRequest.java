package com.yassir.budgetbuddy.note.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NoteRequest(
        Integer id,
        @NotNull(message = "Title name cannot be null")
        @NotEmpty(message = "Title name cannot be empty")
        String title,
        @NotNull(message = "Content name cannot be null")
        @NotEmpty(message = "Content name cannot be empty")
        String content,
        Integer expenseId
) {
}
