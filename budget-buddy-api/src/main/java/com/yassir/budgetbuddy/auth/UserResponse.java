package com.yassir.budgetbuddy.auth;


import lombok.*;

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
    private String role;
}
