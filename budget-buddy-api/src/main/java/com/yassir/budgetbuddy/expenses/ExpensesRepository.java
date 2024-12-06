package com.yassir.budgetbuddy.expenses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

    Optional<Expenses> findByName(String name);
}
