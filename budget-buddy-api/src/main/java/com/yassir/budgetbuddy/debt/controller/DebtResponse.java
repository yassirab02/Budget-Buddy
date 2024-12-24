package com.yassir.budgetbuddy.debt.controller;

import com.yassir.budgetbuddy.debt.DebtType;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebtResponse {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate issueDate;
    private boolean isPaid;
    private String type;
    private String owner; // Full name of the user who owns the debt
}
