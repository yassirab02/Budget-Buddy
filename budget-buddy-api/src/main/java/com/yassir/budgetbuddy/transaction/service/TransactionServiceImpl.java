package com.yassir.budgetbuddy.transaction.service;

import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.goal.repository.GoalRepository;
import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.transaction.TransactionRepository;
import com.yassir.budgetbuddy.transaction.TransactionStatus;
import com.yassir.budgetbuddy.transaction.TransactionType;
import com.yassir.budgetbuddy.transaction.controller.TransactionMapper;
import com.yassir.budgetbuddy.transaction.controller.TransactionRequest;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;
import com.yassir.budgetbuddy.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final GoalRepository goalRepository;
    private final WalletRepository walletRepository;

    @Override
    public Integer transferMoneyToGoal(TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException {

        User user = (User) connectedUser.getPrincipal();

        Wallet sourceWallet = walletRepository.findById(request.sourceWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Source wallet not found"));

        if (request.transactionType() != TransactionType.TRANSFER_TO_GOAL) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        BigDecimal amount = request.amount();

        if (goalRepository.findById(request.goalId()).isEmpty()) {
            throw new IllegalArgumentException("Goal not found");
        }

        Goal goal = goalRepository.findById(request.goalId()).get();

        // Check if the source wallet has enough funds
        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientResourcesException("Not enough funds in the source wallet");
        }

        // Subtract the amount from the source wallet
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));

        // Add the amount to the goal's current amount
        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));

        // Create a new transaction for the transfer
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setDate(LocalDate.now());
        transaction.setDestinationWallet(null);
        transaction.setStatus(TransactionStatus.PENDING); // Initially set to PENDING
        transaction.setSender(user);
        transaction.setReceiver(user);

        // Save the transaction (assuming repository pattern)
        transactionRepository.save(transaction);

        // Optionally, update the status based on success or failure
        transaction.setStatus(TransactionStatus.SUCCESS); // Assuming success for now
        transactionRepository.save(transaction);

        // Update goal status if necessary
        goal.checkGoalStatus();

        return transaction.getId();
    }

}
