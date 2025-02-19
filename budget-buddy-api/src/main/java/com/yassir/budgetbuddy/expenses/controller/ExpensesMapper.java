package com.yassir.budgetbuddy.expenses.controller;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.expenses.ExpensesType;
import com.yassir.budgetbuddy.income.controller.IncomeResponse;
import com.yassir.budgetbuddy.note.controller.NoteResponse;
import com.yassir.budgetbuddy.note.repository.NoteRepository;
import com.yassir.budgetbuddy.wallet.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpensesMapper {

    public Expenses toExpenses(ExpensesRequest request){
        return Expenses.builder()
                .id(request.id())
                .name(request.name())
                .amount(request.amount())
                .description(request.description())
                .date(request.date())
                .type(request.expensesType())
                .category(ExpensesCategory.builder()
                        .id(request.categoryId())
                        .build())
                .budget(Budget.builder()
                        .id(request.budgetId())
                        .build())
                .wallet(Wallet.builder() // Assuming you need to map wallet too
                        .id(request.walletId()) // Add a walletId field if needed in the request
                        .build())
                .build();
    }

    public ExpensesResponse toExpensesResponse(Expenses expenses) {
        List<NoteResponse> noteResponses = expenses.getNotes().stream()
                .map(note -> new NoteResponse(
                        note.getId(),
                        note.getTitle(),
                        note.getContent(),
                        note.getCreatedDate()
                ))
                .collect(Collectors.toList());
        return ExpensesResponse.builder()
                .id(expenses.getId())
                .name(expenses.getName())
                .amount(expenses.getAmount())
                .description(expenses.getDescription())
                .date(expenses.getDate())
                .archived(expenses.isArchived())
                .expensesType(expenses.getType() != null ? expenses.getType().name() : "Unknown")
                .category(expenses.getCategory() != null ? expenses.getCategory().getName() : "Unknown")
                .budget(expenses.getBudget() != null ? expenses.getBudget().getName() : "Unknown")
                .wallet(expenses.getWallet() != null ? expenses.getWallet().getName() : "Unknown")
                .notes(noteResponses)
                .build();
    }

}
