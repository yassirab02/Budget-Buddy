package com.yassir.budgetbuddy.category.controller;

import com.yassir.budgetbuddy.category.bean.IncomeSource;
import com.yassir.budgetbuddy.category.controller.response.ExpensesCategoryResponse;
import com.yassir.budgetbuddy.category.controller.response.IncomeSourceResponse;
import com.yassir.budgetbuddy.category.service.facade.ExpensesCategoryService;
import com.yassir.budgetbuddy.category.service.facade.IncomeSourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    @GetMapping("expenses-category")
    public List<ExpensesCategoryResponse> getExpensesCategory() {
        return expensesCategoryService.findAll();
    }

    @GetMapping("income-source")
    public List<IncomeSourceResponse> getIncomeSources() {
        return incomeSourceService.findAll();
    }

    private final IncomeSourceService incomeSourceService;
    private final ExpensesCategoryService expensesCategoryService;

}
