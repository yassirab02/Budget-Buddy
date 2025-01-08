package com.yassir.budgetbuddy.saving.controller;


import com.yassir.budgetbuddy.saving.Saving;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SavingMapper {

    public SavingResponse toSavingResponse(Saving saving) {
        return SavingResponse.builder()
                .id(saving.getId())
                .amount(saving.getAmount())
                .month(saving.getMonth())
                .year(saving.getYear())
                .fullName(saving.getUser().fullName())
                .build();
    }

}