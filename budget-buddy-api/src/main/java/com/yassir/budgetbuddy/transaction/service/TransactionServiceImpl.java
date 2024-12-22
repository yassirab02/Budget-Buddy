package com.yassir.budgetbuddy.transaction.service;

import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.transaction.TransactionRepository;
import com.yassir.budgetbuddy.transaction.TransactionStatus;
import com.yassir.budgetbuddy.transaction.TransactionType;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    @Override
    public void transferMoneyToGoal(Wallet sourceWallet, Goal goal, BigDecimal amount, User sender) throws InsufficientResourcesException {
        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientResourcesException("Not enough funds in the source wallet");
        }

        // Subtract the amount from the source wallet
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));

        // Add the amount to the goal's current amount
        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));

        // Create a new transaction for the transfer
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());
        transaction.setTransactionType(TransactionType.TRANSFER_TO_GOAL); // Use the new type
        transaction.setStatus(TransactionStatus.PENDING); // Initially set to PENDING
        transaction.setSourceWallet(sourceWallet);
        transaction.setGoal(goal); // Set the goal as the destination
        transaction.setSender(sender);

        // Save the transaction (assuming repository pattern)
        transactionRepository.save(transaction);

        // Optionally, update the status based on success or failure
        transaction.setStatus(TransactionStatus.SUCCESS); // Assuming success for now
        transactionRepository.save(transaction);

        // Update goal status if necessary
        goal.checkGoalStatus();
    }

}
