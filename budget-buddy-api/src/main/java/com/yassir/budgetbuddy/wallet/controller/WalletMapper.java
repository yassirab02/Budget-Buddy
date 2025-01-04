package com.yassir.budgetbuddy.wallet.controller;

import com.yassir.budgetbuddy.category.bean.CurrencyType;
import com.yassir.budgetbuddy.expenses.controller.ExpensesResponse;
import com.yassir.budgetbuddy.income.controller.IncomeResponse;
import com.yassir.budgetbuddy.wallet.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletMapper {

    public Wallet toWallet(WalletRequest request) {
        return Wallet.builder()
                .id(request.id()) // If `id` is null, it's a new wallet
                .name(request.name())
                .balance(request.balance())
                .totalIncome(request.totalIncome())
                .totalExpenses(request.totalExpenses())
                .currencyType(CurrencyType.builder()
                        .id(request.currencyTypeId())
                        .build())
                .build();
    }

    public WalletResponse toWalletResponse(Wallet wallet) {
        List<IncomeResponse> incomeResponses = wallet.getIncomes().stream()
                .map(income -> new IncomeResponse(
                        income.getId(),
                        income.getName(),
                        income.getAmount(),
                        income.getDate() != null ? income.getDate().toString() : null,  // Convert LocalDate to String
                        income.getDescription(),
                        income.getIncomeSource().getName(),  // Assuming getIncomeSource() returns an object with a getName() method
                        income.getWallet().getName()  // Assuming getWallet() returns an object with a getName() method
                ))
                .collect(Collectors.toList());

        List<ExpensesResponse> expenseResponses = wallet.getExpenses().stream()
                .map(expense -> new ExpensesResponse(
                        expense.getId(),
                        expense.getName(),  // Ensure the name is passed correctly
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getDate(),  // LocalDate is already correct, so pass it as is
                        expense.getCategory().getName(),  // Assuming getCategory() returns an object with a getName() method
                        expense.getBudget() != null ? expense.getBudget().getName() : null,  // Handle null values if necessary
                        expense.getWallet().getName()  // Assuming getWallet() returns an object with a getName() method
                ))
                .collect(Collectors.toList());

        return WalletResponse.builder()
                .id(wallet.getId())
                .name(wallet.getName())
                .balance(wallet.getBalance())
                .totalIncome(wallet.getTotalIncome())
                .totalExpenses(wallet.getTotalExpenses())
                .currencyType(wallet.getCurrencyType().getName())
                .owner(wallet.getOwner().fullName())
                .incomes(incomeResponses)
                .expenses(expenseResponses)
                .build();
    }
}
