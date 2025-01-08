package com.yassir.budgetbuddy.budget.controller;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetResponse {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal amount;
    private Double targetAmount;
    private Double limitAmount;
    private BigDecimal usedAmount;     // Calculated used amount
    private BigDecimal remainingAmount; // Calculated remaining amount
    private LocalDate startDate;
    private LocalDate endDate;
    private byte[] budgetCover;
    private String owner; // Full name of the user who owns the budget

}
