package com.yassir.budgetbuddy.wallet.repository;

import com.yassir.budgetbuddy.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Integer>, JpaSpecificationExecutor<Wallet> {

    Optional<Wallet> findByName(String name);

}
