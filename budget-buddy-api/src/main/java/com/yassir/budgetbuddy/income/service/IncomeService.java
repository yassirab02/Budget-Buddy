package com.yassir.budgetbuddy.income.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.controller.ExpensesResponse;
import com.yassir.budgetbuddy.income.controller.IncomeRequest;
import com.yassir.budgetbuddy.income.controller.IncomeResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;


public interface IncomeService {

    Integer addOrUpdateIncome(@Valid IncomeRequest request);

    void deleteIncome(Integer incomeId);

    PageResponse<IncomeResponse> findAllIncomes(int page, int size, Authentication connectedUser);

    IncomeResponse findIncomeById(Integer incomeId);
}
