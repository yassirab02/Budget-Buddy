package com.yassir.budgetbuddy.debt;

import com.yassir.budgetbuddy.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Integer> {

    // Fetch unpaid debts for a user
    List<Debt> findByUserAndIsPaidFalse(User user);

    // Fetch all debts for a user
    List<Debt> findByUser(User user);

    // Fetch debts by creditor
    List<Debt> findByUserAndCreditor(User user, String creditor);

    // Fetch debts due within a date range
    List<Debt> findByUserAndDueDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
