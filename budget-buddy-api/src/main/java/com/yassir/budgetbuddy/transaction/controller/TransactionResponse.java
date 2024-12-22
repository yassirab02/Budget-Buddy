package com.yassir.budgetbuddy.transaction.controller;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private Integer id;
    private String description;
    private BigDecimal amount;
    private String category;
    private String date;
    private String type;
    private String account;
    private String user;
    private String currency;
    private String status;
}
