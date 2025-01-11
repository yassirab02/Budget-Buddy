package com.yassir.budgetbuddy.category.service.impl;

import com.yassir.budgetbuddy.category.Repository.ExpensesCategoryRepository;
import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import com.yassir.budgetbuddy.category.service.facade.ExpensesCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpensesCategoryServiceImpl implements ExpensesCategoryService {

    private final ExpensesCategoryRepository  expensesCategoryRepository;

    @Override
    public List<ExpensesCategory> findAll() {
        List<ExpensesCategory> expensesCategories = expensesCategoryRepository.findAll();
        if (expensesCategories.isEmpty()) {
            throw new RuntimeException("No expenses category found");
        }
        return expensesCategories;
    }
}
