package com.yassir.budgetbuddy.income.controller;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.income.service.IncomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("income")
@RequiredArgsConstructor
@Tag(name = "Income")
public class IncomeController {

    private final IncomeService service;

    @PostMapping("/add-income")
    public ResponseEntity<Integer> addOrUpdateIncome(
            @RequestBody @Valid IncomeRequest request
    ) {
        return ResponseEntity.ok(service.addOrUpdateIncome(request));
    }

    @GetMapping("/all-incomes")
    public ResponseEntity<PageResponse<IncomeResponse>> findAllIncomes(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllIncomes(page, size,connectedUser));
    }

    @GetMapping("/{income-id}")
    public ResponseEntity<IncomeResponse> findIncomeById(
            @PathVariable("income-id") Integer incomeId
    ) {
        return ResponseEntity.ok(service.findIncomeById(incomeId));
    }


    @DeleteMapping("/{income-id}")
    public ResponseEntity<?> deleteIncome(
            @PathVariable("income-id") Integer incomeId
    ) {
        service.deleteIncome(incomeId);
        return ResponseEntity.noContent().build();
    }
}
