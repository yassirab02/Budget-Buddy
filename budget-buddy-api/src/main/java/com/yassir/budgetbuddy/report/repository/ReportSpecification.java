package com.yassir.budgetbuddy.report.repository;

import com.yassir.budgetbuddy.report.Report;
import org.springframework.data.jpa.domain.Specification;

public class ReportSpecification {
    public static Specification<Report> withUserId(Integer userId) {

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

}