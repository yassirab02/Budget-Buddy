package com.yassir.budgetbuddy.contact.controller;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactResponse {
    private Integer id;
    private String sender;
    private String email;
    private String subject;
    private String message;
    private String state;
}
