package com.yassir.budgetbuddy.goal.controller;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoalResponse {

    private Integer id;
    private String name; // Goal name (e.g., "Save for Vacation")
    private String description; // Description of the goal
    private BigDecimal targetAmount; // Amount user wants to save
    private BigDecimal currentAmount; // Current saved amount
    private LocalDate startDate; // Start date for the goal
    private LocalDate targetDate; // Target date to achieve the goal
    private boolean reached; // Whether the goal has been reached or not
    private String owner; // Full name of the user who owns the goal
}
