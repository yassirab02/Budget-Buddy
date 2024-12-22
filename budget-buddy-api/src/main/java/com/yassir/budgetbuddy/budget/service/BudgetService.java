package com.yassir.budgetbuddy.budget.service;

import com.yassir.budgetbuddy.budget.controller.BudgetRequest;
import com.yassir.budgetbuddy.budget.controller.BudgetResponse;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.controller.ExpensesRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface BudgetService {

    Integer addOrUpdateBudget(BudgetRequest request, Authentication connectedUser);

    void uploadBudgetCoverPicture(MultipartFile file, Authentication connectedUser, Integer budgetId);

    void deleteBudget(Integer budgetId, Authentication connectedUser);

    PageResponse<BudgetResponse> findAllBudgetsByOwner(int page, int size, Authentication connectedUser);

    BudgetResponse findBudgetById(Integer budgetId);
}
