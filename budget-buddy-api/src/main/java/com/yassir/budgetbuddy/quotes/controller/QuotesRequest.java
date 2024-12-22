package com.yassir.budgetbuddy.quotes.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuotesRequest(

        Integer id, // Optional, null when creating a new budget
        @NotNull(message = "Quote author cannot be null")
        @NotEmpty(message = "Quote author cannot be empty")
        String author,
        @NotNull(message = "Quote cannot be null")
        @NotEmpty(message = "Quote cannot be empty")
        String quote,

        String budgetCover // Optional field for budget cover image

) {
}
