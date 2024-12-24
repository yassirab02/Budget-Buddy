package com.yassir.budgetbuddy.wallet;

import com.yassir.budgetbuddy.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Integer>, JpaSpecificationExecutor<Wallet> {

    Optional<Wallet> findByName(String name);

}
