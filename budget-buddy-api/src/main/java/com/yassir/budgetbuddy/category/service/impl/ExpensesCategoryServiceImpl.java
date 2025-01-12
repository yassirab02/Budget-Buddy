package com.yassir.budgetbuddy.category.service.impl;

import com.yassir.budgetbuddy.category.Repository.ExpensesCategoryRepository;
import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import com.yassir.budgetbuddy.category.controller.mapper.ExpensesCategoryMapper;
import com.yassir.budgetbuddy.category.controller.response.ExpensesCategoryResponse;
import com.yassir.budgetbuddy.category.service.facade.ExpensesCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpensesCategoryServiceImpl implements ExpensesCategoryService {

    private final ExpensesCategoryRepository  expensesCategoryRepository;
    private final ExpensesCategoryMapper expensesCategoryMapper; // Injecting the mapper

    @Override
    public List<ExpensesCategoryResponse> findAll() {
        List<ExpensesCategory> expensesCategories = expensesCategoryRepository.findAll();
        if (expensesCategories.isEmpty()) {
            throw new RuntimeException("No expenses category found");
        }

        // Map the list of ExpensesCategory entities to a list of ExpensesCategoryResponse DTOs
        return expensesCategories.stream()
                .map(expensesCategoryMapper::toExpensesCategoryResponse)
                .collect(Collectors.toList());
    }
}
