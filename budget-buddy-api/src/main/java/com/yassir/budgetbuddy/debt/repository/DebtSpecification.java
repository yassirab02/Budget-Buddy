package com.yassir.budgetbuddy.debt.repository;

import com.yassir.budgetbuddy.debt.Debt;
import org.springframework.data.jpa.domain.Specification;

public class DebtSpecification {
    public static Specification<Debt> withOwnerId(Integer ownerId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }

}