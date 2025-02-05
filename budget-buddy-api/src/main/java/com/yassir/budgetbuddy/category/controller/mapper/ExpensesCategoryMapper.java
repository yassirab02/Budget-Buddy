package com.yassir.budgetbuddy.category.controller.mapper;

import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import com.yassir.budgetbuddy.category.controller.request.ExpensesCategoryRequest;
import com.yassir.budgetbuddy.category.controller.response.ExpensesCategoryResponse;
import com.yassir.budgetbuddy.file.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ExpensesCategoryMapper {

    // Map the ExpensesCategoryRequest to ExpensesCategory entity
    public ExpensesCategory toExpensesCategory(ExpensesCategoryRequest request) {
        return ExpensesCategory.builder()
                .id(request.id()) // Optional field, can be null for new categories
                .name(request.name())
                .description(request.description())
                .totalExpenses(BigDecimal.valueOf(0.0)) // Initialize the total expenses to 0
                .build();
    }

    // Map ExpensesCategory entity to ExpensesCategoryResponse DTO
    public ExpensesCategoryResponse toExpensesCategoryResponse(ExpensesCategory category) {
        return ExpensesCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .iconUrl(FileUtils.readFileFromLocation(category.getIcon_url()))
                .build();
    }
}
