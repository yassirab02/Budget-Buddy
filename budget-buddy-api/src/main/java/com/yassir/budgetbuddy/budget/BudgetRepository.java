package com.yassir.budgetbuddy.budget;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    Optional<Budget> findByName(String name);
}
