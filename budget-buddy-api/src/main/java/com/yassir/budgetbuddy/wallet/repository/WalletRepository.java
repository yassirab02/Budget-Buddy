package com.yassir.budgetbuddy.wallet.repository;

import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.wallet.Wallet;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Integer>, JpaSpecificationExecutor<Wallet> {

    Optional<Wallet> findByName(String name);

    @Query("""
            select w from Wallet w
            where w.owner = :user
            """)
    List<Wallet> findAllByOwner(User user);

    @Query("""
            select w from Wallet w
            where w.name = :name and w.owner = :user
            """)
    Optional<Wallet> findByNameAndOwner(@NotNull(message = "100") String name, User user);

    @Query("""
            select w from Wallet w
            where w.owner = :user
            """)
    List<Wallet> findAllByUser(User user);

}
