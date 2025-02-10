package com.yassir.budgetbuddy.user.controller;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTransferResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String fullName;
}
