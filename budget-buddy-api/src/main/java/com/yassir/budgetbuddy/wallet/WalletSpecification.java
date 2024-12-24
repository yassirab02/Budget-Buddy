package com.yassir.budgetbuddy.wallet;

import com.yassir.budgetbuddy.budget.Budget;
import org.springframework.data.jpa.domain.Specification;

public class WalletSpecification {
    public static Specification<Wallet> withOwnerId(Integer ownerId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }

}