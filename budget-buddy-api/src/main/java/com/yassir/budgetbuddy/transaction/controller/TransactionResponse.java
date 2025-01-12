package com.yassir.budgetbuddy.transaction.controller;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private Integer id;
    private String message;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String transactionType; // E.g., TRANSFER, DEPOSIT, WITHDRAWAL
    private String status; // E.g., PENDING, COMPLETED
    private String sourceWallet;
    private String destinationWallet;
    private String sender;
    private String receiver;
    private Integer goalId;
}
