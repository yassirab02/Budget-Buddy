package com.yassir.budgetbuddy.wallet.controller;


import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.service.UserService;
import com.yassir.budgetbuddy.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("wallet")
@RequiredArgsConstructor
@Tag(name = "Wallet")
public class WalletController {

    private final WalletService service;
    private final UserService userService;

    @PostMapping("/add-wallet")
    public ResponseEntity<Integer> addOrUpdateWallet(
            @RequestBody @Valid WalletRequest request,
            Authentication connectedUser
    ) {

        return ResponseEntity.ok(service.addOrUpdateWallet(request,connectedUser));
    }

    @GetMapping(("/all-wallets"))
    public ResponseEntity<PageResponse<WalletResponse>> findAllWalletsByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllWalletsByOwner(page, size, connectedUser));
    }


    @GetMapping("/{wallet-id}")
    public ResponseEntity<WalletResponse> findWalletById(
            @PathVariable("wallet-id") Integer walletId
    ) {
        return ResponseEntity.ok(service.findWalletById(walletId));
    }


    @DeleteMapping("/{wallet-id}")
    public ResponseEntity<?> deleteWallet(
            @PathVariable("wallet-id") Integer walletId,
            Authentication connectedUser
    ) {
        service.deleteWallet(walletId, connectedUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total-balance")
    public BigDecimal getTotalBalance(
            Authentication connectedUser
    ) {
        User user = (User) connectedUser.getPrincipal();
        return userService.getTotalBalance(user);

    }

    @PostMapping("/add-amount/{wallet-id}/{amount}")
    public ResponseEntity<?> addAmount(
            @PathVariable("wallet-id") Integer walletId,
            @PathVariable("amount") BigDecimal amount,
            Authentication connectedUser
    ) {
        service.addAmount(walletId,amount, connectedUser);
        return ResponseEntity.accepted().build();
    }
}
