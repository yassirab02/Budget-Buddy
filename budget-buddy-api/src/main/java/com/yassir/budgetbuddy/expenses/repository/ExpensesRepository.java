package com.yassir.budgetbuddy.expenses.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.expenses.Expenses;
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

public interface ExpensesRepository extends JpaRepository<Expenses, Integer>, JpaSpecificationExecutor<Expenses> {

    Optional<Expenses> findByName(String name);

    Optional<List<Expenses>> findByBudget(Budget budget);

    @Query("SELECT e FROM Expenses e WHERE e.wallet.owner.id = :userId")
    List<Expenses> findAllByUserId(@Param("userId") Integer userId);

    @Query("SELECT e FROM Expenses e WHERE e.wallet.owner = :user AND e.date BETWEEN :startDate AND :endDate")
    List<Expenses> findByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT e FROM Expenses e
            WHERE e.wallet.owner = :user
            AND e.name = :name
            """)
    Optional<Expenses> findByNameAndWalletId(@NotNull(message = "Expense name cannot be null") @NotEmpty(message = "Expense name cannot be empty") String name, @NotNull(message = "Wallet ID is required") Integer integer);
}
