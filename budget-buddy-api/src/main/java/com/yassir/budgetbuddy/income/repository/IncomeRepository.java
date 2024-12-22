package com.yassir.budgetbuddy.income.repository;

import com.yassir.budgetbuddy.income.Income;
import com.yassir.budgetbuddy.user.User;
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
}
