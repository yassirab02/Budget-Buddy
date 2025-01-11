package com.yassir.budgetbuddy.category.service.facade;

import com.yassir.budgetbuddy.category.bean.ExpensesCategory;

import java.util.List;

public interface ExpensesCategoryService {
    List<ExpensesCategory> findAll();
}
