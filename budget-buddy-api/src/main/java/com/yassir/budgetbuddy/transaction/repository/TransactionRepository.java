package com.yassir.budgetbuddy.transaction.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> , JpaSpecificationExecutor<Transaction> {


}
