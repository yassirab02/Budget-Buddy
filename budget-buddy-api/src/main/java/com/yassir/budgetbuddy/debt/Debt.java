package com.yassir.budgetbuddy.debt;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.story.StoryStatus;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Debt extends BaseEntity {

    private String name; // name of the debt

    private BigDecimal amount; // Total debt amount

    @Column(nullable = false)
    private LocalDate dueDate; // Due date for repayment

    @Column(nullable = false)
    private LocalDate issueDate; // Date when the debt was taken

    @Column(nullable = false)
    private boolean isPaid; // Indicates if the debt is paid

    @Lob
    private String description; // Description or notes about the debt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DebtType type;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner; // The user who owns this debt
}
