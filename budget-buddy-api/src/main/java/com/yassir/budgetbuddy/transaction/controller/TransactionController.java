package com.yassir.budgetbuddy.transaction.controller;

import com.yassir.budgetbuddy.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.InsufficientResourcesException;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions")
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/sent-transactions")
    public ResponseEntity<Integer> addOrUpdateBudget(
            @RequestBody @Valid TransactionRequest request,
            Authentication connectedUser) throws InsufficientResourcesException {

        return ResponseEntity.ok(service.transferMoneyToGoal(request, connectedUser));
    }


}
