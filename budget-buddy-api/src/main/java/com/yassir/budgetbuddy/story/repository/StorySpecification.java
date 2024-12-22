package com.yassir.budgetbuddy.story.repository;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.story.Story;
import org.springframework.data.jpa.domain.Specification;

public class StorySpecification {
    public static Specification<Story> withOwnerId(Integer ownerId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }

}