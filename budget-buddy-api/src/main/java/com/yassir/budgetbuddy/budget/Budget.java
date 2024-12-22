package com.yassir.budgetbuddy.budget;


import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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


    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;




}
