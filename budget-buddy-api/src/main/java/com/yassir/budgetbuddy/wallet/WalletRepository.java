package com.yassir.budgetbuddy.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Integer> {

    Optional<Wallet> findByName(String name);

}
