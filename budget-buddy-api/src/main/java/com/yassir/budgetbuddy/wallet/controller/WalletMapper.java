package com.yassir.budgetbuddy.wallet.controller;

import com.yassir.budgetbuddy.category.bean.CurrencyType;
import com.yassir.budgetbuddy.wallet.Wallet;
import com.yassir.budgetbuddy.wallet.WalletResponse;
import org.springframework.stereotype.Service;

@Service
public class WalletMapper {

    public Wallet toWallet(WalletRequest request) {
        return Wallet.builder()
                .id(request.id()) // If `id` is null, it's a new wallet
                .name(request.name())
                .balance(request.balance())
                .currencyType(CurrencyType.builder()
                        .id(request.currencyTypeId())
                        .build())
                .build();
    }

}
