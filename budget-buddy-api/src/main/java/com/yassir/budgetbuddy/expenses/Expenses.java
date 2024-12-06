package com.yassir.budgetbuddy.expenses;

import com.yassir.budgetbuddy.budget.Budget;
import com.yassir.budgetbuddy.category.Category;
import com.yassir.budgetbuddy.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expenses extends BaseEntity {


    private String name;
    private Double amount;
    private String description;
    private String type;

    @ManyToOne
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


}
