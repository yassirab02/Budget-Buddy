package com.yassir.budgetbuddy.state;

import com.yassir.budgetbuddy.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ContactState extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String code;

    @Column(unique = true, nullable = false)
    private String name; // Example: "Pending", "In Progress", "Resolved"

    private String description; // Optional: Additional information about the state
}
