package com.yassir.budgetbuddy.budget.controller;

import lombok.*;

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
    private Double amount;
    private Double targetAmount;
    private Double limitAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private byte[] budgetCover;
    private String owner; // Full name of the user who owns the budget

}
