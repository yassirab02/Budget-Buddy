package com.yassir.budgetbuddy.category.bean;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.expenses.Expenses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesCategory {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., "Food", "Transport"

    @Column
    private String description; // Optional, for extra details

    @Column(nullable = false)
    private String icon_url; // URL or file path for the category icon


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Expenses> expenses;

}