package com.yassir.budgetbuddy.contact.repository;

import com.yassir.budgetbuddy.contact.Contact;
import org.springframework.data.jpa.domain.Specification;

public class ContactSpecification {
    public static Specification<Contact> withUserId(Integer userId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

}