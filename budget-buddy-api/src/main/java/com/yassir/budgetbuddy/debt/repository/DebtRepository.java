package com.yassir.budgetbuddy.debt.repository;

import com.yassir.budgetbuddy.debt.Debt;
import com.yassir.budgetbuddy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface DebtRepository extends JpaRepository<Debt, Integer> ,JpaSpecificationExecutor<Debt> {

    List<Debt> findByOwnerAndIssueDateBefore(User owner, LocalDate endDate);

    List<Debt> findByOwnerAndIsPaidTrueAndIssueDateBefore(User owner, LocalDate endDate);

    @Query("SELECT d FROM Debt d WHERE d.owner = :user AND YEAR(d.issueDate) = :year")
    List<Debt> findByOwnerAndYear(@Param("user") User user, @Param("year") int year);

    @Query("SELECT d FROM Debt d WHERE d.owner = :user AND YEAR(d.issueDate) = :year AND d.isPaid = :isPaid")
    List<Debt> findByOwnerAndYearAndIsPaid(@Param("user") User user, @Param("year") int year, @Param("isPaid") boolean isPaid);
}
