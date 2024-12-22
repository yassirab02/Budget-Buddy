package com.yassir.budgetbuddy.budget.repository;

import com.yassir.budgetbuddy.budget.Budget;
import org.springframework.data.jpa.domain.Specification;

public class BudgetSpecification {
    public static Specification<Budget> withOwnerId(Integer ownerId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }

}