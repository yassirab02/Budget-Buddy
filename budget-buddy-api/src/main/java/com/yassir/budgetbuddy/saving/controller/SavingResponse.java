package com.yassir.budgetbuddy.saving.controller;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingResponse {

    private Integer id;
    private Double amount;
    private Integer month;
    private Integer year;
    private String fullName; // Full name of the user who owns the saving
}