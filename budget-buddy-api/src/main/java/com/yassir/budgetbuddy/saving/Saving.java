package com.yassir.budgetbuddy.saving;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Saving extends BaseEntity {


    private Double amount; // Savings for the given period

    private Integer month; // 1 = January, 2 = February, etc.

    private Integer year; // The year this savings entry corresponds to


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
