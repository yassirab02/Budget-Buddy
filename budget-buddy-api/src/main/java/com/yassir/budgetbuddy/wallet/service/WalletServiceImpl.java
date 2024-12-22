package com.yassir.budgetbuddy.wallet.service;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.wallet.WalletResponse;
import com.yassir.budgetbuddy.wallet.controller.WalletRequest;
import com.yassir.budgetbuddy.wallet.Wallet;
import com.yassir.budgetbuddy.wallet.controller.WalletMapper;
import com.yassir.budgetbuddy.wallet.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;


    @Override
    @Transactional
    public Integer addOrUpdateWallet(WalletRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Wallet wallet = walletMapper.toWallet(request);
        wallet.setOwner(user);

        return repository.save(wallet).getId();
    }

    @Override
    public void deleteWallet(Integer walletId, Authentication connectedUser) {
        boolean condition = (walletId != null);
        if (condition) {
            User user = ((User) connectedUser.getPrincipal());
            Wallet wallet = repository.findById(walletId).orElseThrow();
            if (wallet.getOwner().getId().equals(user.getId())) {
                repository.deleteById(walletId);
            }
        }
    }

}
