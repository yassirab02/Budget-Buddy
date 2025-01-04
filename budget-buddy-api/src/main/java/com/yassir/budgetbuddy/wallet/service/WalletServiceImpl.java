package com.yassir.budgetbuddy.wallet.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.wallet.controller.WalletResponse;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;
    private final WalletMapper walletMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public Integer addOrUpdateWallet(WalletRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Find if a wallet with the same name already exists for the user
        Optional<Wallet> existingWallet = repository.findByNameAndOwner(request.name(), user);
        if (existingWallet.isPresent()) {
            throw new EntityNotFoundException("Wallet with the name already exists");
        }
        Wallet wallet = walletMapper.toWallet(request);
        wallet.setOwner(user);
        user.setTotalBalance(user.getTotalBalance().add(wallet.getBalance()));
        return repository.save(wallet).getId();
    }


    @Override
    public void deleteWallet(Integer walletId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (walletId != null) {
            Wallet wallet = repository.findById(walletId)
                    .orElseThrow(() -> new EntityNotFoundException("No Wallet found with the Id : " + walletId));
            if (wallet.getOwner().getId().equals(user.getId())) {
                user.setTotalBalance(user.getTotalBalance().subtract(wallet.getBalance()));
                repository.deleteById(walletId);
            }
        }
    }

    @Override
    public PageResponse<WalletResponse> findAllWalletsByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
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
