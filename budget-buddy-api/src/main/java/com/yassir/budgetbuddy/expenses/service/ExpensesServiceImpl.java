package com.yassir.budgetbuddy.expenses.service;


import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.expenses.repository.ExpensesRepository;
import com.yassir.budgetbuddy.expenses.controller.ExpensesMapper;
import com.yassir.budgetbuddy.expenses.controller.ExpensesRequest;
import com.yassir.budgetbuddy.expenses.controller.ExpensesResponse;
import com.yassir.budgetbuddy.expenses.repository.ExpensesSpecification;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpensesServiceImpl implements ExpensesService {

    private final ExpensesRepository repository;
    private final ExpensesMapper expensesMapper;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public Integer addOrUpdateExpense(ExpensesRequest request) {
        Expenses expense;
        BigDecimal previousAmount = BigDecimal.ZERO;

        if (request.id() == null) {
            // Handle creating a new expense
            Optional<Expenses> existingExpense = repository.findByNameAndWalletId(request.name(), request.walletId());
            if (existingExpense.isPresent()) {
                throw new EntityNotFoundException("Expense with the name already exists");
            }
        } else {
            // Handle updating an existing expense
            expense = repository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + request.id()));
            previousAmount = expense.getAmount(); // Store the previous amount for balance adjustments
        }

        Optional<Wallet> wallet = walletRepository.findById(request.walletId());
        expense = expensesMapper.toExpenses(request);

        wallet.ifPresent(expense::setWallet);

        if (expense.getWallet() != null) {
            Wallet walletEntity = expense.getWallet();
            User owner = walletEntity.getOwner();

            // Adjust wallet and owner balances
            BigDecimal amountDifference = expense.getAmount().subtract(previousAmount);

            walletEntity.setBalance(walletEntity.getBalance().subtract(amountDifference));
            walletEntity.setTotalExpenses(walletEntity.getTotalExpenses().add(amountDifference));

            // Ensure the changes are persisted
            walletRepository.save(walletEntity); // Save the updated wallet

            if (owner != null) {
                owner.setTotalBalance(owner.getTotalBalance().subtract(amountDifference));
                userRepository.save(owner); // Save the updated user (owner)
            }
        }

        // Save and return the expense ID
        return repository.save(expense).getId();
    }


    @Override
    public PageResponse<ExpensesResponse> findAllExpenses(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Create a pageable object with sorting by createdDate in descending order
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Fetch expenses using the specification to filter by user ID
        Page<Expenses> expenses = repository.findAll(ExpensesSpecification.withUserId(user.getId()), pageable);

        List<ExpensesResponse> expensesResponse = expenses.stream()
                .map(expensesMapper::toExpensesResponse)
                .toList();
        return new PageResponse<>(
                expensesResponse,
                expenses.getNumber(),
                expenses.getSize(),
                expenses.getTotalElements(),
                expenses.getTotalPages(),
                expenses.isFirst(),
                expenses.isLast()
        );
    }


    @Override
    public ExpensesResponse findExpenseById(Integer expenseId) {
        Expenses expense = repository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("No Expense found with the Id : " + expenseId));
        return expensesMapper.toExpensesResponse(expense);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteExpense(Integer expenseId) {
        Expenses expense = repository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("No Expense found with the Id: " + expenseId));

        if (expense.getWallet() != null) {
            Wallet wallet = expense.getWallet();
            User owner = wallet.getOwner();
            BigDecimal expenseAmount = expense.getAmount();

            // Update wallet and owner balances
            wallet.setTotalExpenses(wallet.getTotalExpenses().subtract(expenseAmount));
            wallet.setBalance(wallet.getBalance().add(expenseAmount));

            if (owner != null) {
                owner.setTotalBalance(owner.getTotalBalance().add(expenseAmount));
            }

            // Save updated wallet and owner entities
            walletRepository.save(wallet);  // Save the updated wallet
            if (owner != null) {
                userRepository.save(owner);  // Save the updated owner (user)
            }

            // Delete the expense entry from the repository
            repository.deleteById(expenseId);
        }
    }


    @Override
    public void resetExpenses(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        List<Expenses> expenses = repository.findAll(ExpensesSpecification.withUserId(user.getId()));

        if (expenses.isEmpty()) {
            throw new EntityNotFoundException("No expenses found to reset");
        }

        // Update and save all expenses
        expenses.forEach(expense -> expense.setArchived(true));
        repository.saveAll(expenses);
    }


    @Override
    @Transactional
    public void resetMonthlyExpense(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        LocalDate now = LocalDate.now();

        // Check if there are any expenses for the current month
        boolean hasExpenses = repository.existsByMonthAndUserId(now.getMonthValue(), user.getId());
        if (!hasExpenses) {
            throw new EntityNotFoundException("No expenses found for the current month");
        }

        // Archive the expenses in bulk
        repository.archiveExpensesByMonthAndUserId(now.getMonthValue(), user.getId());
    }



}
