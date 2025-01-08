package com.yassir.budgetbuddy.income.repository;

import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income,Integer> , JpaSpecificationExecutor<Income> {

    Optional<Income> findByName(String name);

    @Query("SELECT i FROM Income i WHERE i.wallet.owner = :user AND i.date BETWEEN :startDate AND :endDate")
    List<Income> findByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT i FROM Income i
            WHERE i.name = :name
            AND i.wallet.id = :integer
            """)
    Optional<Income> findByNameAndWalletId(@NotNull(message = "Income name cannot be null") @NotEmpty(message = "Income name cannot be empty") String name, @NotNull(message = "Wallet ID is required") Integer integer);

    @Query("""
        SELECT COALESCE(SUM(i.amount), 0) FROM Income i
        WHERE i.wallet.owner.id = :id
        AND FUNCTION('MONTH', i.date) = :month
        AND FUNCTION('YEAR', i.date) = :year
        """)
    Double getTotalIncomeForUserAndMonth(@Param("id") Integer id, @Param("month") Integer month, @Param("year") Integer year);

}
