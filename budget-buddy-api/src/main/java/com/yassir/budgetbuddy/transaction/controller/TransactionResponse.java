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
    private Integer sourceWalletId;
    private Integer destinationWalletId;
    private Integer senderId;
    private Integer receiverId;
    private Integer goalId;
}
