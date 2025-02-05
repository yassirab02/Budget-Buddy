package com.yassir.budgetbuddy.expenses.controller;

import com.yassir.budgetbuddy.category.controller.response.ExpensesCategoryResponse;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.service.ExpensesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses")
public class ExpensesController {

    private final ExpensesService service;

    @PostMapping("/add-expense")
    public ResponseEntity<Integer> addOrUpdateExpense(
            @RequestBody @Valid ExpensesRequest request
    ) {

        return ResponseEntity.ok(service.addOrUpdateExpense(request));
    }

    @GetMapping("/all-expenses")
    public ResponseEntity<PageResponse<ExpensesResponse>> findAllExpenses(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllExpenses(page, size,connectedUser));
    }

    @GetMapping("/{expense-id}")
    public ResponseEntity<ExpensesResponse> findExpenseById(
            @PathVariable("expense-id") Integer expenseId
    ) {
        return ResponseEntity.ok(service.findExpenseById(expenseId));
    }

    @DeleteMapping("/{expense-id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable("expense-id") Integer expenseId
    ) {
        service.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-expenses")
    public ResponseEntity<?> resetExpenses(
            Authentication connectedUser
    ) {
        service.resetExpenses(connectedUser);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-monthly-expenses")
    public ResponseEntity<?> resetMonthlyExpenses(
            Authentication connectedUser
    ) {
        service.resetMonthlyExpense(connectedUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top-spending-categories")
    public ResponseEntity<List<ExpensesCategoryResponse>> getTopSpendingCategories(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.getTopSpendingCategories(connectedUser));
    }

}
