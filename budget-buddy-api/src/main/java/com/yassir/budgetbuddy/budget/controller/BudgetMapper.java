package com.yassir.budgetbuddy.budget.controller;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.expenses.repository.ExpensesRepository;
import com.yassir.budgetbuddy.file.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


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
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }

    public BudgetResponse toBudgetResponse(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .name(budget.getName())
                .description(budget.getDescription())
                .amount(budget.getAmount())
                .targetAmount(budget.getTargetAmount())
                .limitAmount(budget.getLimitAmount())
                .startDate(budget.getStartDate())
                .endDate(budget.getEndDate())
                .budgetCover(FileUtils.readFileFromLocation(budget.getBudgetCover()))
                .owner(budget.getOwner().fullName())
                .build();
    }


}
