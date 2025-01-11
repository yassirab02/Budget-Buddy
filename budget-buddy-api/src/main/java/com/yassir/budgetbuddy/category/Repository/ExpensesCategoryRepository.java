package com.yassir.budgetbuddy.category.Repository;

import com.yassir.budgetbuddy.category.bean.ExpensesCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpensesCategoryRepository extends JpaRepository<ExpensesCategory,Integer> {

    Optional<ExpensesCategory> findByName(String name);

    List<ExpensesCategory> findAll();
}
