package com.yassir.budgetbuddy.budget;


import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Budget extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal amount;
    private Double targetAmount;
    private Double limitAmount;
    private String budgetCover;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // One-to-many relationship with Expenses
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<Expenses> expenses;

    // Method to calculate how much of the budget has been used
    public BigDecimal getUsedAmount() {
        if (expenses == null || expenses.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return expenses.stream()
                .map(Expenses::getAmount)  // Sum the 'amount' field from Expenses (BigDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);  // Return the sum of expenses as BigDecimal
    }

    // Method to calculate remaining budget
    public BigDecimal getRemainingAmount() {
        if (amount == null) {
            return BigDecimal.ZERO;
        }
        return amount.subtract(getUsedAmount());  // Subtract used amount from the total allocated amount
    }
}
