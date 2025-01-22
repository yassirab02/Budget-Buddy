package com.yassir.budgetbuddy.transaction.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.goal.repository.GoalRepository;
import com.yassir.budgetbuddy.transaction.Transaction;
import com.yassir.budgetbuddy.transaction.controller.TransactionResponse;
import com.yassir.budgetbuddy.transaction.repository.TransactionRepository;
import com.yassir.budgetbuddy.transaction.TransactionStatus;
import com.yassir.budgetbuddy.transaction.TransactionType;
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

    @Transactional
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
        if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) > 0) {
            throw new InsufficientResourcesException("Goal already reached");
        }


        // Create a new transaction for the transfer
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setDate(LocalDate.now());
        transaction.setDestinationWallet(null);
        transaction.setStatus(TransactionStatus.PENDING); // Initially set to PENDING
        transaction.setSender(user);
        transaction.setReceiver(user);

        // Save the transaction (assuming repository pattern)
        repository.save(transaction);

        // Optionally, update the status based on success or failure
        transaction.setStatus(TransactionStatus.SUCCESS); // Assuming success for now
        repository.save(transaction);

        // Update goal status if necessary
        goal.checkGoalStatus();
        goalRepository.save(goal);

        return transaction.getId();
    }

    @Transactional
    @Override
    public Integer transferMoneyToWallet(TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException {

        User user = (User) connectedUser.getPrincipal();

        Wallet sourceWallet = walletRepository.findById(request.sourceWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Source wallet not found"));

        Wallet destinationWallet = walletRepository.findById(request.destinationWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Destination wallet not found"));

        if (sourceWallet.getId().equals(destinationWallet.getId())) {
            throw new IllegalArgumentException("Source wallet and destination wallet cannot be the same");
        }


        if (request.transactionType() != TransactionType.TRANSFER_TO_WALLET) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        BigDecimal amount = request.amount();

        // Check if the source wallet has enough funds
        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientResourcesException("Not enough funds in the source wallet");
        }

        // Subtract the amount from the source wallet
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));

        // Add the amount to the destination wallet
        destinationWallet.setBalance(destinationWallet.getBalance().add(amount));

        // Create a new transaction for the transfer
        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setDate(LocalDate.now());
        transaction.setStatus(TransactionStatus.PENDING); // Initially set to PENDING
        transaction.setSender(user);
        transaction.setReceiver(destinationWallet.getOwner());
        transaction.setGoal(null);

        // Save the transaction (assuming repository pattern)
        repository.save(transaction);

        // Optionally, update the status based on success or failure
        transaction.setStatus(TransactionStatus.SUCCESS); // Assuming success for now
        repository.save(transaction);

        return transaction.getId();
    }

    @Override
    @Transactional // Ensures atomicity of the entire operation
    public Integer transferMoneyToUser(TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException {
        User user = (User) connectedUser.getPrincipal();

        if (request.receiverId() == null) {
            throw new IllegalArgumentException("Receiver ID must not be null");
        }
        User receiver = userRepository.findById(request.receiverId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found with ID: " + request.receiverId()));

        if (request.transactionType() != TransactionType.TRANSFER_TO_USER) {
            throw new IllegalArgumentException("Invalid transaction type. Expected TRANSFER.");
        }

        if (request.sourceWalletId() == null) {
            throw new IllegalArgumentException("Source wallet ID must not be null");
        }
        Wallet sourceWallet = walletRepository.findById(request.sourceWalletId())
                .orElseThrow(() -> new IllegalArgumentException("Source wallet not found with ID: " + request.sourceWalletId()));

        BigDecimal amount = request.amount();

        // Check for sufficient funds
        if (sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientResourcesException("Insufficient funds in your wallet");
        }

        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
        user.setTotalBalance(user.getTotalBalance().subtract(amount));

        userRepository.save(user);

        receiver.setTotalBalance(receiver.getTotalBalance().add(amount));

        userRepository.save(receiver);

        Transaction transaction = transactionMapper.toTransaction(request);
        transaction.setDate(LocalDate.now());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setSender(user);
        transaction.setSourceWallet(sourceWallet);
        transaction.setReceiver(receiver);
        transaction.setGoal(null);
        transaction.setDestinationWallet(null);

        // Save the transaction
        transaction = repository.save(transaction);

        // Mark the transaction as successful
        transaction.setStatus(TransactionStatus.SUCCESS);
        repository.save(transaction);

        return transaction.getId();
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


}
