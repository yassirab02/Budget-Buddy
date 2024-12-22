package com.yassir.budgetbuddy.category.bean;

import com.yassir.budgetbuddy.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class IncomeSource {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., Employer, Freelance, Investments, etc.

    @Column
    private String description; // Optional, for extra details

    private String icon_url; // URL or file path for the icon

}
