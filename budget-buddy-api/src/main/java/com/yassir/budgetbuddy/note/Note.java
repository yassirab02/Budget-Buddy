package com.yassir.budgetbuddy.note;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.expenses.Expenses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Note extends BaseEntity {

    private String title;

    @Column(columnDefinition = "TEXT") // For longer text content
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id") // Foreign key in the Note table
    private Expenses expense;
}
