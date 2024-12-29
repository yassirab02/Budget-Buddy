package com.yassir.budgetbuddy.income.controller;

import com.yassir.budgetbuddy.category.bean.IncomeSource;
import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.wallet.Wallet;
import org.springframework.stereotype.Service;

@Service
public class IncomeMapper {

    public Income toIncome(IncomeRequest request) {
        return Income.builder()
                .id(request.id())
                .name(request.name())
                .amount(request.amount())
                .description(request.description())
                .date(request.date())
                .incomeSource(IncomeSource.builder()
                        .id(request.incomeSourceId())
                        .build())
                .build();
    }

    public IncomeResponse toIncomeResponse(Income income) {
        return IncomeResponse.builder()
                .id(income.getId())
                .name(income.getName())
                .amount(income.getAmount())
                .description(income.getDescription())
                .date(String.valueOf(income.getDate()))
                .incomeSource(income.getIncomeSource().getName())
                .wallet(income.getWallet().getName())
                .build();
    }


    public void updateIncomeFromRequest(IncomeRequest request, Income income) {
        if (request.name() != null) {
            income.setName(request.name());
        }
        if (request.amount() != null) {
            income.setAmount(request.amount());
        }
        if (request.description() != null) {
            income.setDescription(request.description());
        }
        if (request.date() != null) {
            income.setDate(request.date());
        }
        if (request.incomeSourceId() != null) {
            income.setIncomeSource(IncomeSource.builder()
                    .id(request.incomeSourceId())
                    .build());
        }
    }
}
