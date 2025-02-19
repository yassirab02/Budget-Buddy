package com.yassir.budgetbuddy.state.controller;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactStateResponse {
    private Integer id;
    private String code;
    private String name;
    private String description;
}
