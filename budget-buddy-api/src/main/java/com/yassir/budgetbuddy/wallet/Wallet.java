package com.yassir.budgetbuddy.wallet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wallet extends BaseEntity {

    private String name;
    private BigDecimal balance;

    @Builder.Default
    private BigDecimal totalIncome = BigDecimal.ZERO;
    @Builder.Default
    private BigDecimal totalExpenses = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private WalletType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private User owner;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Income> incomes;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Expenses> expenses;

    @OneToMany(mappedBy = "sourceWallet", cascade = CascadeType.ALL)
    private List<Transaction> sentTransfers;

    @OneToMany(mappedBy = "destinationWallet", cascade = CascadeType.ALL)
    private List<Transaction> receivedTransfers;


}
