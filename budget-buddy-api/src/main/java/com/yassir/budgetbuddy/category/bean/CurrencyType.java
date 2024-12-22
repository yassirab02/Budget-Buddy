package com.yassir.budgetbuddy.category.bean;

import com.yassir.budgetbuddy.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyType{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String code; // ISO 4217 numeric code (e.g., 840 for USD)

    @Column(nullable = false, unique = true)
    private String name; // Full name of the currency (e.g., United States Dollar)

    @Column(nullable = false, unique = true)
    private String symbol; // Currency symbol (e.g., $, €, ¥)
}
