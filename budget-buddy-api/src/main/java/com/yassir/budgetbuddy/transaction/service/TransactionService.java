package com.yassir.budgetbuddy.transaction.service;

import com.yassir.budgetbuddy.budget.controller.BudgetResponse;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.transaction.controller.TransactionRequest;
import com.yassir.budgetbuddy.transaction.controller.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import javax.naming.InsufficientResourcesException;

public interface TransactionService {

    Integer transferMoneyToGoal(TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException;

    Integer transferMoneyToWallet(@Valid TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException;

    Integer transferMoneyToUser(TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException;

    PageResponse<TransactionResponse> findAllTransactionsBySender(int page, int size, Authentication connectedUser);

    PageResponse<TransactionResponse> findAllTransactionsByReceiver(int page, int size, Authentication connectedUser);
}
