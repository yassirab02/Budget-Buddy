package com.yassir.budgetbuddy.report;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseEntity {

    private LocalDate startDate; // Start date of the report period
    private LocalDate endDate;   // End date of the report period
    private BigDecimal totalIncome;   // Total income during the period
    private BigDecimal totalExpenses; // Total expenses during the period
    private Integer totalGoalsReached; // Total goals reached during the period
    private Integer totalGoals; // Total goals
    private BigDecimal balance;// Remaining balance during the period
    private BigDecimal totalDebt; // Total debt
    private BigDecimal totalPaidDebt; // Total paid debt
    private BigDecimal totalUnpaidDebt; // Total unpaid debt
    @Lob
    private String details; // Additional report details, e.g., summary or analytics
    private int year;   // Year of the report
    private int month;  // Month of the report

    @Column(nullable = true)
    private Integer mostSpendingMonth; // Most spending month of the year

    @Column(nullable = true)
    private BigDecimal savingRate; // Percentage of income saved (e.g., 0.20 for 20%)

    @Enumerated(EnumType.STRING)
    private ReportType type; // MONTHLY or YEARLY

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
