package com.yassir.budgetbuddy.income;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Income extends BaseEntity {

    private String name;
    private Double amount;
    private String description;
    private String type; // salary, bonus, etc
    private String source; // employer, freelance, etc
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
