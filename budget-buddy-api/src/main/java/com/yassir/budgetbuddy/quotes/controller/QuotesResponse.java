package com.yassir.budgetbuddy.quotes.controller;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuotesResponse {

    private Integer id;

    private String quote;

    private String author;

    private byte[] quotePhoto;

    private LocalDate dateOfDisplay;
}
