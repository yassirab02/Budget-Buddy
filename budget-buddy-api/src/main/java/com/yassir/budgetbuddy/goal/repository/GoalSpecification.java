package com.yassir.budgetbuddy.goal.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.goal.Goal;
import org.springframework.data.jpa.domain.Specification;

public class GoalSpecification {
    public static Specification<Goal> withUserId(Integer ownerId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), ownerId);
    }

}