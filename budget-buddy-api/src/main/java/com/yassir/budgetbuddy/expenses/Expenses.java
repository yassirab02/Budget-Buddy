package com.yassir.budgetbuddy.expenses;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.wallet.Wallet;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Expenses extends BaseEntity {


    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private boolean archived;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ExpensesCategory category;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;


}
