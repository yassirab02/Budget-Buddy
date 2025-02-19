package com.yassir.budgetbuddy.expenses;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.note.Note;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;
import jakarta.persistence.*;
import lombok.*;
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
public class Expenses extends BaseEntity {


    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private boolean archived;

    @Enumerated(EnumType.STRING)
    private ExpensesType type;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ExpensesCategory category;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    @JsonBackReference
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    @JsonIgnore
    private Wallet wallet;

}
