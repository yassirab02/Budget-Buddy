package com.yassir.budgetbuddy.report.controller;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {

    private Integer id;
    private LocalDate startDate;  // Start date of the report period
    private LocalDate endDate;    // End date of the report period
    private BigDecimal totalIncome;  // Total income during the period
    private BigDecimal totalExpenses;  // Total expenses during the period
    private int totalGoalsReached;   // Total goals reached during the period
    private int totalGoals;   // Total goals
    private int totalDebt;   // Total Debt
    private int totalPaidDebt;   // Total Paid Debt
    private BigDecimal balance;     // Remaining balance during the period
    private String details;         // Additional report details
    private int year;               // Year of the report
    private int month;              // Month of the report
    private int mostSpendingMonth;              // Month of the report
    private BigDecimal savingRate; // Percentage of income saved (e.g., 0.20 for 20%)
    private String type;            // Type of the report (e.g., monthly, yearly)
    private String owner;           // Full name of the user who owns the report
}
