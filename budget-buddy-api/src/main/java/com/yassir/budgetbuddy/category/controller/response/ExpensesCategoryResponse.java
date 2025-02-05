package com.yassir.budgetbuddy.category.controller.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpensesCategoryResponse {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal totalExpenses;
    private byte[] iconUrl;
    private String parentCategoryName; // The parent category name if available
    private List<ExpensesCategoryResponse> subcategories; // List of subcategories (if any)
}
