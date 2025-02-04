package com.yassir.budgetbuddy.expenses.controller;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate date;
    private boolean archived;
    private String category;
    private String expensesType; // E.g., Fixed, Variable
    private String budget;
    private String wallet;
}
