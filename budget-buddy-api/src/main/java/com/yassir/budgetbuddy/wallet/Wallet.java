package com.yassir.budgetbuddy.wallet;

import com.yassir.budgetbuddy.category.bean.CurrencyType;
import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private BigDecimal totalIncome = BigDecimal.ZERO;
    private BigDecimal totalExpenses = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "currency_type_id", nullable = false)
    private CurrencyType currencyType;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomes;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    private List<Expenses> expenses;

    @OneToMany(mappedBy = "sourceWallet", cascade = CascadeType.ALL)
    private List<Transaction> sentTransfers;

    @OneToMany(mappedBy = "destinationWallet", cascade = CascadeType.ALL)
    private List<Transaction> receivedTransfers;


}
