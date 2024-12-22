package com.yassir.budgetbuddy.transaction.service;

import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;

import javax.naming.InsufficientResourcesException;
import java.math.BigDecimal;

public interface TransactionService {
    void transferMoneyToGoal(Wallet sourceWallet, Goal goal, BigDecimal amount, User sender) throws InsufficientResourcesException;
}
