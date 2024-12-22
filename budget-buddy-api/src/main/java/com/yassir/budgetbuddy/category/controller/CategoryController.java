package com.yassir.budgetbuddy.category.controller;

import com.yassir.budgetbuddy.category.service.facade.ExpensesCategoryService;
import com.yassir.budgetbuddy.category.service.facade.IncomeSourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final IncomeSourceService incomeSourceService;
    private final ExpensesCategoryService expensesCategoryService;

}
