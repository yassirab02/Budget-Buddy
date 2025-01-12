package com.yassir.budgetbuddy.category.service.facade;

import com.yassir.budgetbuddy.category.controller.response.ExpensesCategoryResponse;

import java.util.List;

public interface ExpensesCategoryService {
    List<ExpensesCategoryResponse> findAll();
}
