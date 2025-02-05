package com.yassir.budgetbuddy.budget.repository;

import com.yassir.budgetbuddy.budget.Budget;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Integer> , JpaSpecificationExecutor<Budget> {

    Optional<Budget> findByName(String name);

    @Query("""
            select b from Budget b
            where b.name = :name
            and b.owner.id = :id
            """)
    Optional<Budget> findByNameAndOwnerId(@NotNull(message = "Budget name cannot be null") @NotEmpty(message = "Budget name cannot be empty") String name, Integer id);

    List<Budget> findByOwnerId(Integer id);

    // Fetch budgets for the current month based on createdDate
    @Query("SELECT b FROM Budget b WHERE b.owner.id = :userId AND MONTH(b.createdDate) = :currentMonth AND YEAR(b.createdDate) = :currentYear")
    List<Budget> findBudgetsForCurrentMonth(@Param("userId") Integer userId, @Param("currentMonth") int currentMonth, @Param("currentYear") int currentYear);
}
