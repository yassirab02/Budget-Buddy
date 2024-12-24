package com.yassir.budgetbuddy.transaction.service;

import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.transaction.controller.TransactionRequest;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;
import org.springframework.security.core.Authentication;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;

public interface TransactionService {

    Integer transferMoneyToGoal(TransactionRequest request, Authentication connectedUser) throws InsufficientResourcesException;
}
