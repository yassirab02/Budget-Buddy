package com.yassir.budgetbuddy.wallet.controller;


import com.yassir.budgetbuddy.wallet.WalletResponse;
import com.yassir.budgetbuddy.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wallet")
@RequiredArgsConstructor
@Tag(name = "Wallet")
public class WalletController {

    private final WalletService service;

    @PostMapping("/add-wallet")
    public ResponseEntity<Integer> addOrUpdateWallet(
            @RequestBody @Valid WalletRequest request,
            Authentication connectedUser
    ) {

        return ResponseEntity.ok(service.addOrUpdateWallet(request,connectedUser));
    }


    @DeleteMapping("/{wallet-id}")
    public ResponseEntity<?> deleteWallet(
            @PathVariable("wallet-id") Integer walletId,
            Authentication connectedUser
    ) {
        service.deleteWallet(walletId, connectedUser);
        return ResponseEntity.noContent().build();
    }
}
