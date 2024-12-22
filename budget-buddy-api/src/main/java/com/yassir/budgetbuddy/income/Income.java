package com.yassir.budgetbuddy.income;

import com.yassir.budgetbuddy.category.bean.IncomeSource;
import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.wallet.Wallet;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Income extends BaseEntity {

    private String name;
    private BigDecimal amount;
    private String description;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "source_type_id", nullable = false)
    private IncomeSource incomeSource; // Link to the source type (e.g., Employer, Freelance)

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

}
