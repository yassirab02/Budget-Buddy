package com.yassir.budgetbuddy.budget;


import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.expenses.Expenses;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Budget extends BaseEntity {

    private String name;
    private String description;
    private Double amount;
    private Double targetAmount;
    private Double limitAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String budgetCover;

    @OneToMany(mappedBy = "budget")
    private List<Expenses> expenses;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
