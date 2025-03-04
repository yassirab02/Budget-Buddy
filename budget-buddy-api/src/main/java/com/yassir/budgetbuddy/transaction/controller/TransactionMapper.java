package com.yassir.budgetbuddy.transaction.controller;

import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.wallet.Wallet;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.transaction.TransactionType;
import com.yassir.budgetbuddy.transaction.TransactionStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public Transaction toTransaction(TransactionRequest request) {
        return Transaction.builder()
                .id(request.id())
                .message(request.message())
                .description(request.description())
                .amount(request.amount())
                .transactionType(request.transactionType())
                .sourceWallet(Wallet.builder()
                        .id(request.sourceWalletId())
                        .build())
                .destinationWallet(Wallet.builder()
                                .id(request.destinationWalletId())
                        .build())
                .receiver(User.builder()
                        .id(request.receiverId())
                        .build())
                .goal(Goal.builder()
                        .id(request.goalId())
                        .build())
                .build();
    }

    public TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .message(transaction.getMessage())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .date(transaction.getDate())
                .transactionType(transaction.getTransactionType().getTypeName())
                .status(transaction.getStatus().getStatus())
                .sourceWallet(transaction.getSourceWallet().getName())
                .destinationWallet(transaction.getDestinationWallet() != null ? transaction.getDestinationWallet().getName() : null)
                .sender(transaction.getSender().fullName())
                .receiver(transaction.getReceiver() != null ? transaction.getReceiver().fullName() : null)
                .goalName(transaction.getGoal() != null ? transaction.getGoal().getName() : null)
                .build();
    }
}
