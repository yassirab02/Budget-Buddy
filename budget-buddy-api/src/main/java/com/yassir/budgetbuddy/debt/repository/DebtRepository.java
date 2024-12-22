package com.yassir.budgetbuddy.debt.repository;

import com.yassir.budgetbuddy.debt.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface DebtRepository extends JpaRepository<Debt, Integer> ,JpaSpecificationExecutor<Debt> {

}
