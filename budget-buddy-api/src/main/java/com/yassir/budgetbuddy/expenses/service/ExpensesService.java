package com.yassir.budgetbuddy.expenses.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.controller.ExpensesRequest;
import com.yassir.budgetbuddy.expenses.controller.ExpensesResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface ExpensesService {

    Integer addOrUpdateExpense(@Valid ExpensesRequest request);

    void deleteExpense(Integer expenseId);

    ExpensesResponse findExpenseById(Integer expenseId);

    PageResponse<ExpensesResponse> findAllExpenses(int page, int size, Authentication connectedUser);
}
