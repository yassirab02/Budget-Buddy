package com.yassir.budgetbuddy.expenses.repository;

import com.yassir.budgetbuddy.expenses.Expenses;
import org.springframework.data.jpa.domain.Specification;

public class ExpensesSpecification {

    public static Specification<Expenses> withUserId(Integer userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("wallet").get("owner").get("id"), userId);
    }

}