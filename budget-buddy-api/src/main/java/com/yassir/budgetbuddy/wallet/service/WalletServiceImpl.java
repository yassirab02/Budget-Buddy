package com.yassir.budgetbuddy.wallet.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.wallet.WalletResponse;
import com.yassir.budgetbuddy.wallet.repository.WalletSpecification;
import com.yassir.budgetbuddy.wallet.controller.WalletRequest;
import com.yassir.budgetbuddy.wallet.Wallet;
import com.yassir.budgetbuddy.wallet.controller.WalletMapper;
import com.yassir.budgetbuddy.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageResponse<WalletResponse> findAllWalletsByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Wallet> wallets = repository.findAll(WalletSpecification.withOwnerId(user.getId()), pageable);
        List<WalletResponse> walletResponse = wallets.stream()
                .map(walletMapper::toWalletResponse)
                .toList();
        return new PageResponse<>(
                walletResponse,
                wallets.getNumber(),
                wallets.getSize(),
                wallets.getTotalElements(),
                wallets.getTotalPages(),
                wallets.isFirst(),
                wallets.isLast()
        );
    }

    @Override
    public WalletResponse findWalletById(Integer walletId) {
        Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("No Wallet found with the Id : " + walletId));
        return walletMapper.toWalletResponse(wallet);
    }

}
