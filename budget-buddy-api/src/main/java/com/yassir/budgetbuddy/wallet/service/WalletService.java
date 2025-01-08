package com.yassir.budgetbuddy.wallet.service;


import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.wallet.controller.WalletResponse;
import com.yassir.budgetbuddy.wallet.controller.WalletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

public interface WalletService {


    Integer addOrUpdateWallet(@Valid WalletRequest request, Authentication connectedUser);

    void deleteWallet(Integer walletId, Authentication connectedUser);

    PageResponse<WalletResponse> findAllWalletsByOwner(int page, int size, Authentication connectedUser);

    WalletResponse findWalletById(Integer walletId);

    void addAmount(Integer walletId, BigDecimal amount, Authentication connectedUser);
}
