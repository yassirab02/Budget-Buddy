package com.yassir.budgetbuddy.user.controller;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private BigDecimal totalBalance;
    private LocalDateTime createdAt;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private boolean isNew;
    private String role;
}
