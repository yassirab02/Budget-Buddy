package com.yassir.budgetbuddy.transaction.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.transaction.Transaction;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {
    public static Specification<Transaction> withSenderId(Integer senderId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sender").get("id"), senderId);
    }

    public static Specification<Transaction> withReceiverId(Integer receiverId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("receiver").get("id"), receiverId);
    }

    public static Specification<Transaction> withSenderIdOrReceiverId(Integer userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(root.get("sender").get("id"), userId),
                criteriaBuilder.equal(root.get("receiver").get("id"), userId)
        );
    }

}