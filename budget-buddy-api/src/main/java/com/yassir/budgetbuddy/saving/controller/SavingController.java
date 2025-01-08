package com.yassir.budgetbuddy.saving.controller;

import com.yassir.budgetbuddy.budget.controller.BudgetResponse;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.saving.service.SavingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("saving")
@RequiredArgsConstructor
@Tag(name = "Saving")
public class SavingController {

    private final SavingService service;

    @PostMapping("calc-savings")
    public ResponseEntity<SavingResponse> calculateAndSaveSavings(
            Authentication connectedUser) {
        return ResponseEntity.ok(service.calculateAndSaveSavings(connectedUser));
    }

    @GetMapping(("/all-savings"))
    public ResponseEntity<PageResponse<SavingResponse>> findAllSavingsByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllSavingsByUser(page, size, connectedUser));
    }

    @GetMapping(("/find-by-month/{month}"))
    public ResponseEntity<SavingResponse> findSavingByMonth(
            @PathVariable("month") Integer month,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findSavingByMonth(month,connectedUser));
    }

}
