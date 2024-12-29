package com.yassir.budgetbuddy.income.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.income.repository.IncomeRepository;
import com.yassir.budgetbuddy.income.controller.IncomeMapper;
import com.yassir.budgetbuddy.income.controller.IncomeRequest;
import com.yassir.budgetbuddy.income.controller.IncomeResponse;
import com.yassir.budgetbuddy.income.repository.IncomeSpecification;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.wallet.Wallet;
import com.yassir.budgetbuddy.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository repository;
    private final IncomeMapper incomeMapper;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public Integer addOrUpdateIncome(IncomeRequest request) {
        Income income;
        BigDecimal previousAmount = BigDecimal.ZERO;

        if (request.id() == null) {
            // Handle creating a new income
            Optional<Income> existingIncome = repository.findByNameAndWalletId(request.name(), request.walletId());
            if (existingIncome.isPresent()) {
                throw new EntityNotFoundException("Income with the name already exists");
            }
        } else {
            // Handle updating an existing income
            income = repository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("Income not found with id: " + request.id()));
            previousAmount = income.getAmount(); // Store the previous amount for balance adjustments
        }

        income = incomeMapper.toIncome(request);

        // Ensure the wallet exists
        Wallet wallet = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found with id: " + request.walletId()));

        income.setWallet(wallet);

        // Adjust wallet and owner balances
        BigDecimal amountDifference = income.getAmount().subtract(previousAmount);

        wallet.setBalance(wallet.getBalance().add(amountDifference));
        wallet.setTotalIncome(wallet.getTotalIncome().add(amountDifference));

        // Ensure the changes are persisted
        walletRepository.save(wallet); // Save the updated wallet

        if (wallet.getOwner() != null) {
            User owner = wallet.getOwner();
            owner.setTotalBalance(owner.getTotalBalance().add(amountDifference));

            userRepository.save(owner); // Save the updated user (owner)
        }

        // Save and return the income ID
        return repository.save(income).getId();
    }

    @Override
    public void deleteIncome(Integer incomeId) {
        if (incomeId != null) {
            Income income = repository.findById(incomeId)
                    .orElseThrow(() -> new EntityNotFoundException("No Income found with the Id: " + incomeId));

            if (income.getWallet() != null) {
                // Update wallet and owner balances
                Wallet wallet = income.getWallet();
                User owner = wallet.getOwner();
                BigDecimal incomeAmount = income.getAmount();

                // Subtract the income amount from wallet and owner balances
                wallet.setBalance(wallet.getBalance().subtract(incomeAmount));
                wallet.setTotalIncome(wallet.getTotalIncome().subtract(incomeAmount));

                if (owner != null) {
                    owner.setTotalBalance(owner.getTotalBalance().subtract(incomeAmount));
                }

                // Save updated wallet and owner entities
                walletRepository.save(wallet);  // Save the updated wallet
                if (owner != null) {
                    userRepository.save(owner);  // Save the updated owner (user)
                }

                // Delete the income entry from the repository
                repository.deleteById(incomeId);
            } else {
                throw new EntityNotFoundException("No Income found with the Id: " + incomeId);
            }
        }
    }

    @Override
    public PageResponse<IncomeResponse> findAllIncomes(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Income> incomes = repository.findAll(IncomeSpecification.withUserId(user.getId()), pageable);
        List<IncomeResponse> incomeResponse = incomes.stream()
                .map(incomeMapper::toIncomeResponse)
                .toList();
        return new PageResponse<>(
                incomeResponse,
                incomes.getNumber(),
                incomes.getSize(),
                incomes.getTotalElements(),
                incomes.getTotalPages(),
                incomes.isFirst(),
                incomes.isLast()
        );
    }

    @Override
    public IncomeResponse findIncomeById(Integer incomeId) {
        Income income = repository.findById(incomeId)
                .orElseThrow(() -> new EntityNotFoundException("No Income found with the Id : " + incomeId));
        return incomeMapper.toIncomeResponse(income);
    }


}
