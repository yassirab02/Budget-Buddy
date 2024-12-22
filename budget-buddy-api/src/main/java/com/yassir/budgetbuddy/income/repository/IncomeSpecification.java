package com.yassir.budgetbuddy.income.repository;

import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.income.Income;
import org.springframework.data.jpa.domain.Specification;

public class IncomeSpecification {

    public static Specification<Income> withUserId(Integer userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("wallet").get("owner").get("id"), userId);
    }

}