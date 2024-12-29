package com.yassir.budgetbuddy.debt.controller;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.debt.service.DebtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("debt")
@RequiredArgsConstructor
@Tag(name = "Debt")
public class DebtController {

    private final DebtService service;

    @PostMapping("add-debt")
    public ResponseEntity<Integer> addOrUpdateDebt(
            @RequestBody @Valid DebtRequest request,
            Authentication connectedUser) {

        return ResponseEntity.ok(service.addOrUpdateDebt(request, connectedUser));
    }

    @GetMapping(("/all-debts"))
    public ResponseEntity<PageResponse<DebtResponse>> findAllDebtsByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllDebtsByOwner(page, size, connectedUser));
    }

    @GetMapping("/debts-status")
    public ResponseEntity<PageResponse<DebtResponse>> findAllNonPaidDebtsByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "paidStatus") boolean paidStatus,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findDebtsByOwnerAndPaidStatus(page, size, connectedUser, paidStatus));
    }

    @GetMapping("/{debt-id}")
    public ResponseEntity<DebtResponse> findDebtById(
            @PathVariable("debt-id") Integer debtId
    ) {
        return ResponseEntity.ok(service.findDebtById(debtId));
    }

    @DeleteMapping("/{debt-id}")
    public ResponseEntity<?> deleteDebt(
            @PathVariable("debt-id") Integer debtId,
            Authentication connectedUser
    ) {
        service.deleteDebt(debtId, connectedUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total-amount")
    public BigDecimal getTotalAmountDebtByUser(Authentication connectedUser) {
        return service.getTotalAmountDebtByUser(connectedUser);
    }

}

