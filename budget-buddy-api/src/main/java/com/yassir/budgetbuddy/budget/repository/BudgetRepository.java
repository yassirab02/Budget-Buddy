package com.yassir.budgetbuddy.budget.repository;

import com.yassir.budgetbuddy.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Integer> , JpaSpecificationExecutor<Budget> {

    Optional<Budget> findByName(String name);

}
