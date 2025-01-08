package com.yassir.budgetbuddy.saving.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.saving.Saving;
import org.springframework.data.jpa.domain.Specification;

public class SavingSpecification {
    public static Specification<Saving> withUserId(Integer userId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

}