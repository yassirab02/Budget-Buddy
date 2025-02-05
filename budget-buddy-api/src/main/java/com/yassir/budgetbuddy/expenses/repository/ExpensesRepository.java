package com.yassir.budgetbuddy.expenses.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
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
            WHERE e.wallet.id = :walletId
            AND e.name = :name
            """)
    Optional<Expenses> findByNameAndWalletId(@NotNull(message = "Expense name cannot be null") @NotEmpty(message = "Expense name cannot be empty") String name, @NotNull(message = "Wallet ID is required") Integer walletId);

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0) FROM Expenses e
            WHERE e.wallet.owner.id = :id
            AND FUNCTION('MONTH', e.date) = :month
            AND FUNCTION('YEAR', e.date) = :year
            """)
    Double getTotalExpensesForUserAndMonth(@Param("id") Integer id, @Param("month") Integer month, @Param("year") Integer year);

    @Modifying
    @Query("UPDATE Expenses e SET e.archived = true WHERE FUNCTION('MONTH', e.date) = :month AND e.wallet.owner.id = :userId AND e.archived = false")
    void archiveExpensesByMonthAndUserId(Integer month, Integer userId);


    @Query("SELECT e FROM Expenses e WHERE FUNCTION('MONTH', e.date) = :month AND e.wallet.owner.id = :id AND e.archived = false")
    List<Expenses> findNonArchivedByMonthAndUserId(Integer month, Integer id);

    @Query("""
                SELECT e.category, SUM(e.amount) 
                FROM Expenses e 
                WHERE e.wallet.owner.id = :userId 
                GROUP BY e.category 
                ORDER BY SUM(e.amount) DESC
            """)
    List<Object[]> findTopSpendingCategoriesByUser(@Param("userId") Integer userId);


    // Custom query to calculate the total expenses for the current month
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expenses e WHERE e.wallet.owner.id = :userId AND FUNCTION('MONTH', e.createdDate) = :month AND FUNCTION('YEAR', e.createdDate) = :year")
    BigDecimal findTotalExpensesForCurrentMonth(@Param("userId") Integer userId, @Param("month") int month, @Param("year") int year);
}
