package com.yassir.budgetbuddy.quotes.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record QuotesRequest(

        Integer id, // Optional, null when creating a new budget
        @NotNull(message = "Quote author cannot be null")
        @NotEmpty(message = "Quote author cannot be empty")
        String author,
        @NotNull(message = "Quote cannot be null")
        @NotEmpty(message = "Quote cannot be empty")
        String quote,
        @NotNull(message = "Date of display cannot be null")
        @NotEmpty(message = "Date of display cannot be empty")
        LocalDate dateOfDisplay,
        String budgetCover // Optional field for budget cover image

) {
}
