package com.yassir.budgetbuddy.budget.controller;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.file.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
public class BudgetMapper {

    public Budget toBudget (BudgetRequest request){
        return Budget.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .amount(request.amount())
                .targetAmount(request.targetAmount())
                .limitAmount(request.limitAmount())
                .build();
    }

    public BudgetResponse toBudgetResponse(Budget budget) {
        BigDecimal usedAmount = budget.getUsedAmount();
        BigDecimal remainingAmount = budget.getRemainingAmount();
        return BudgetResponse.builder()
                .id(budget.getId())
                .name(budget.getName())
                .description(budget.getDescription())
                .amount(budget.getAmount())
                .targetAmount(budget.getTargetAmount())
                .limitAmount(budget.getLimitAmount())
                .usedAmount(usedAmount)  // Added dynamic calculation of usedAmount
                .remainingAmount(remainingAmount)  // Added dynamic calculation of remainingAmount
                .owner(budget.getOwner().fullName())
                .build();
    }

}
