package com.yassir.budgetbuddy.transaction.controller;

import com.yassir.budgetbuddy.transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(
        Integer id, // Optional if updating, should be null for new transactions

        @NotNull(message = "Message is required")
        String message,

        String description, // Optional description

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "Transaction Type is required")
        TransactionType transactionType, // E.g., TRANSFER, DEPOSIT, WITHDRAWAL

        @NotNull(message = "Transaction Status is required")
        String status, // E.g., PENDING, COMPLETED

        @NotNull(message = "Source Wallet ID is required")
        Integer sourceWalletId,

        Integer destinationWalletId, // Optional for some transaction types

        Integer receiverId, // Optional for some transaction types

        Integer goalId // Optional for goal-related transactions
) {}
