package com.yassir.budgetbuddy.goal;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Goal extends BaseEntity {

    private String name; // Goal name (e.g., "Save for Vacation")
    private String description; // Goal name (e.g., "Save for Vacation")
    private BigDecimal targetAmount; // Amount user wants to save
    private BigDecimal currentAmount; // Current saved amount
    private LocalDate startDate; // Start date for the goal
    private LocalDate targetDate; // Target date to achieve the goal
    private boolean reached; // Whether the goal has been reached or not


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who set the goal

    @OneToMany(mappedBy = "goal")
    private List<Transaction> transactions; // Transactions related to the wallet

    // Logic to check if the goal has been reached
    public void checkGoalStatus() {
        this.reached = this.currentAmount.compareTo(this.targetAmount) >= 0;
    }


}