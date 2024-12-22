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
                .wallet(Wallet.builder()
                        .id(request.walletId())
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
                .source(income.getIncomeSource().getName())
                .wallet(income.getWallet().getName())
                .build();
    }
}
