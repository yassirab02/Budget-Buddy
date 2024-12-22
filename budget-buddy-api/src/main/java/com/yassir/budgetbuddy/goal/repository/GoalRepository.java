package com.yassir.budgetbuddy.goal.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal,Integer> , JpaSpecificationExecutor<Goal> {

    List<Goal> findByUser(User user);

    @Query("SELECT g FROM Goal g WHERE g.user = :user AND g.reached = true")
    Page<Goal> findByUserAndReachedTrue(User user, Pageable pageable);

    @Query("SELECT g FROM Goal g WHERE g.user = :user AND g.reached = false")
    Page<Goal> findByUserAndReachedFalse(User user, Pageable pageable);

    @Query("SELECT g FROM Goal g WHERE g.user = :user AND g.reached = true AND g.targetDate BETWEEN :startDate AND :endDate")
    List<Goal> findByUserAndReachedDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
