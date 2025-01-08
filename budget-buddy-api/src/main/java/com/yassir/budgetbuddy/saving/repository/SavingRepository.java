package com.yassir.budgetbuddy.saving.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.saving.Saving;
import com.yassir.budgetbuddy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SavingRepository extends JpaRepository<Saving,Integer> , JpaSpecificationExecutor<Saving> {

    @Query("""
        SELECT s FROM Saving s
        WHERE s.month = :month
        AND s.year = :year
        AND s.user = :user
        """)
    Optional<Saving> findByMonthAndYearAndUser(@Param("month") Integer month, @Param("year") Integer year, @Param("user") User user);
}
