package com.yassir.budgetbuddy.goal.service;

import com.yassir.budgetbuddy.budget.controller.BudgetResponse;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.goal.controller.GoalRequest;
import com.yassir.budgetbuddy.goal.controller.GoalResponse;
import com.yassir.budgetbuddy.user.User;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface GoalService {

    Integer addOrUpdateBudget(@Valid GoalRequest request, Authentication connectedUser);

    PageResponse<GoalResponse> findAllGoalsByUser(int page, int size, Authentication connectedUser);

    PageResponse<GoalResponse>  findReachedGoalsByUser(int page, int size, Authentication connectedUser);

    // find non-reached goals by user
    PageResponse<GoalResponse> findNonReachedGoalsByUser(int page, int size, Authentication connectedUser);

    void deleteGoal(Integer goalId, Authentication connectedUser);
}