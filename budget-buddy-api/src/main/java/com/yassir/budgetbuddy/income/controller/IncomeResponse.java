package com.yassir.budgetbuddy.income.controller;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeResponse {

    private Integer id;
    private String name;
    private BigDecimal amount;
    private String date;
    private String description;
    private String source;
    private String wallet;

}
