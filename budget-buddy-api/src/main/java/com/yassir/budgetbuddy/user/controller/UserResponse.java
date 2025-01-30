package com.yassir.budgetbuddy.user.controller;


import lombok.*;

import java.math.BigDecimal;

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
    private boolean isNew;
    private String role;
}
