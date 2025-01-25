package com.yassir.budgetbuddy.transaction.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.goal.repository.GoalRepository;
import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.transaction.controller.TransactionResponse;
import com.yassir.budgetbuddy.transaction.repository.TransactionRepository;
import com.yassir.budgetbuddy.transaction.TransactionStatus;
import com.yassir.budgetbuddy.transaction.controller.TransactionMapper;
import com.yassir.budgetbuddy.transaction.controller.TransactionRequest;
import com.yassir.budgetbuddy.transaction.repository.TransactionSpecification;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import com.yassir.budgetbuddy.wallet.Wallet;
import com.yassir.budgetbuddy.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository repository;
    private final GoalRepository goalRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;


    @Override
    public PageResponse<TransactionResponse> findAllTransactions(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Fetch transactions where the user is either the sender or receiver
        Page<Transaction> transactions = repository.findAll(
                TransactionSpecification.withSenderIdOrReceiverId(user.getId()), pageable);

        // Map the transactions to their responses
        List<TransactionResponse> transactionResponse = transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();

        // Create and return the paginated response
        return new PageResponse<>(
                transactionResponse,
                transactions.getNumber(),
                transactions.getSize(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                transactions.isFirst(),
                transactions.isLast()
        );
    }

    @Override
    public PageResponse<TransactionResponse> findAllTransactionsBySender(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Transaction> transactions = repository.findAll(TransactionSpecification.withSenderId(user.getId()), pageable);
        List<TransactionResponse> transactionResponse = transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
        return new PageResponse<>(
                transactionResponse,
                transactions.getNumber(),
                transactions.getSize(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                transactions.isFirst(),
                transactions.isLast()
        );
    }

    @Override
    public PageResponse<TransactionResponse> findAllTransactionsByReceiver(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Transaction> transactions = repository.findAll(TransactionSpecification.withReceiverId(user.getId()), pageable);
        List<TransactionResponse> transactionResponse = transactions.stream()
                .map(transactionMapper::toTransactionResponse)
                .toList();
        return new PageResponse<>(
                transactionResponse,
                transactions.getNumber(),
                transactions.getSize(),
                transactions.getTotalElements(),
                transactions.getTotalPages(),
                transactions.isFirst(),
                transactions.isLast()
        );
    }

    @Transactional
    @Override
    public Integer transferMoney(TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException {
        User user = (User) connectedUser.getPrincipal();

        // Validate the transaction type and perform the corresponding logic
        switch (request.transactionType()) {
            case TRANSFER_TO_GOAL -> {
                return handleTransferToGoal(request, user);
            }
            case TRANSFER_TO_WALLET -> {
                return handleTransferToWallet(request, user);
            }
            case TRANSFER_TO_USER -> {
                return handleTransferToUser(request, user);
            }
            default -> throw new IllegalArgumentException("Unsupported transaction type");
        }
    }

    // Handle Transfer to Goal
    private Integer handleTransferToGoal(TransactionRequest request, User user) throws InsufficientResourcesException {
        Wallet sourceWallet = walletRepository.findById(request.sourceWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Source wallet not found"));

        Goal goal = goalRepository.findById(request.goalId())
                .orElseThrow(() -> new IllegalArgumentException("Goal not found"));

        BigDecimal amount = request.amount();

        // Check if the source wallet has enough funds
        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientResourcesException("Not enough funds in the source wallet");
        }

        // Check if the goal is already reached
        if (goal.getCurrentAmount().add(amount).compareTo(goal.getTargetAmount()) > 0) {
            throw new IllegalArgumentException("Target goal amount exceeded");
        }

        // Update balances
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));
        goal.checkGoalStatus();

        walletRepository.save(sourceWallet);
        goalRepository.save(goal);

        // Save the transaction
        return saveTransaction(request, user, user, sourceWallet, null, goal, TransactionStatus.SUCCESS);
    }

    // Handle Transfer to Wallet
    private Integer handleTransferToWallet(TransactionRequest request, User user) throws InsufficientResourcesException {
        Wallet sourceWallet = walletRepository.findById(request.sourceWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Source wallet not found"));

        Wallet destinationWallet = walletRepository.findById(request.destinationWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Destination wallet not found"));

        BigDecimal amount = request.amount();

        // Check if the source wallet has enough funds
        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientResourcesException("Not enough funds in the source wallet");
        }

        // Update balances
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
        destinationWallet.setBalance(destinationWallet.getBalance().add(amount));

        walletRepository.save(sourceWallet);
        walletRepository.save(destinationWallet);

        // Save the transaction
        return saveTransaction(request, user, destinationWallet.getOwner(), sourceWallet, destinationWallet, null, TransactionStatus.SUCCESS);
    }

    // Handle Transfer to User
    private Integer handleTransferToUser(TransactionRequest request, User user) throws InsufficientResourcesException {
        Wallet sourceWallet = walletRepository.findById(request.sourceWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Source wallet not found"));

        User receiver = userRepository.findById(request.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        BigDecimal amount = request.amount();

        // Check if the source wallet has enough funds
        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientResourcesException("Not enough funds in the source wallet");
        }

        // Update balances
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
        user.setTotalBalance(user.getTotalBalance().subtract(amount));
        receiver.setTotalBalance(receiver.getTotalBalance().add(amount));

        walletRepository.save(sourceWallet);
        userRepository.save(user);
        userRepository.save(receiver);

        // Save the transaction
        return saveTransaction(request, user, receiver, sourceWallet, null, null, TransactionStatus.SUCCESS);
    }

    // Common Transaction Save Logic
    private Integer saveTransaction(TransactionRequest request, User sender, User receiver, Wallet sourceWallet, Wallet destinationWallet, Goal goal, TransactionStatus status) {
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setDate(LocalDate.now());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setSourceWallet(sourceWallet);
        transaction.setDestinationWallet(destinationWallet);
        transaction.setGoal(goal);
        transaction.setStatus(status);

        repository.save(transaction);
        return transaction.getId();
    }



}
