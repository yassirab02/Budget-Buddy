package com.yassir.budgetbuddy.contact.controller;

import com.yassir.budgetbuddy.state.ContactState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ContactRequest(
        Integer id, // Optional, null when creating a new budget
        String email,
        @NotNull(message = "Subject cannot be null")
        @NotEmpty(message = "Subject cannot be empty")
        String subject,
        @NotNull(message = "Message cannot be null")
        @NotEmpty(message = "Message  cannot be empty")
        String message,
        String sender
) {
}
