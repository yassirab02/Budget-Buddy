package com.yassir.budgetbuddy.expenses.controller;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpensesResponse {

    private Integer id;
    private String name;
    private BigDecimal amount;
    private String description;
    private String category;
    private String budget;
    private String wallet;
}
