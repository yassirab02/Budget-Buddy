package com.yassir.budgetbuddy.expenses.controller;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.wallet.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class ExpensesMapper {

    public Expenses toExpenses(ExpensesRequest request){
        return Expenses.builder()
                .id(request.id())
                .name(request.name())
                .amount(request.amount())
                .description(request.description())
                .date(request.date())
                .category(ExpensesCategory.builder()
                        .id(request.categoryId())
                        .build())
                .budget(Budget.builder()
                        .id(request.budgetId())
                        .build())
                .wallet(Wallet.builder()
                        .id(request.walletId())
                        .build())
                .build();
    }

    public ExpensesResponse toExpensesResponse( Expenses expenses) {
        return ExpensesResponse.builder()
                .id(expenses.getId())
                .name(expenses.getName())
                .amount(expenses.getAmount())
                .description(expenses.getDescription())
                .date(expenses.getDate())
                .category(expenses.getCategory().getName())
                .budget(expenses.getBudget().getName())
                .wallet(expenses.getWallet().getName())
                .build();
    }
}
