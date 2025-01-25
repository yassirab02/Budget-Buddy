package com.yassir.budgetbuddy.transaction.controller;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions")
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transfer-money")
    public ResponseEntity<Integer> transferMoney(
            @RequestBody @Valid TransactionRequest request,
            Authentication connectedUser) throws InsufficientResourcesException {

        return ResponseEntity.ok(service.transferMoney(request, connectedUser));
    }


    @GetMapping(("/all-transactions"))
    public ResponseEntity<PageResponse<TransactionResponse>> findAllTransactions(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllTransactions(page, size, connectedUser));
    }

    @GetMapping(("/all-transactions-sent"))
    public ResponseEntity<PageResponse<TransactionResponse>> findAllTransactionsBySender(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllTransactionsBySender(page, size, connectedUser));
    }

    @GetMapping(("/all-transactions-received"))
    public ResponseEntity<PageResponse<TransactionResponse>> findAllTransactionsByReciever(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllTransactionsByReceiver(page, size, connectedUser));
    }

}
