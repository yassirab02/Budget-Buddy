package com.yassir.budgetbuddy.budget.controller;

import com.yassir.budgetbuddy.budget.service.BudgetService;
import com.yassir.budgetbuddy.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("budget")
@RequiredArgsConstructor
@Tag(name = "Budget")
public class BudgetController {

    private final BudgetService service;

    @PostMapping("add-budget")
    public ResponseEntity<Integer> addOrUpdateBudget(
            @RequestBody @Valid BudgetRequest request,
            Authentication connectedUser) {

        return ResponseEntity.ok(service.addOrUpdateBudget(request, connectedUser));
    }

    @PostMapping(value = "/cover/{budget-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBudgetCoverPicture(
            @PathVariable("budget-id") Integer budgetId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ) {
        service.uploadBudgetCoverPicture(file, connectedUser, budgetId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(("/all-budgets"))
    public ResponseEntity<PageResponse<BudgetResponse>> findAllBudgetsByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllBudgetsByOwner(page, size, connectedUser));
    }

    @GetMapping("/{budget-id}")
    public ResponseEntity<BudgetResponse> findBudgetById(
            @PathVariable("budget-id") Integer budgetId
    ) {
        return ResponseEntity.ok(service.findBudgetById(budgetId));
    }

    @DeleteMapping("/{budget-id}")
    public ResponseEntity<?> deleteBudget(
            @PathVariable("budget-id") Integer budgetId,
            Authentication connectedUser
    ) {
        service.deleteBudget(budgetId, connectedUser);
        return ResponseEntity.noContent().build();
    }
}
