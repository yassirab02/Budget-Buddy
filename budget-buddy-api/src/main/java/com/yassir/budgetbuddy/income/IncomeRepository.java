package com.yassir.budgetbuddy.income;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income,Integer> {

    Optional<Income> findByName(String name);
}
