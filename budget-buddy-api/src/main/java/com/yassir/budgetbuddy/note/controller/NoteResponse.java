package com.yassir.budgetbuddy.note.controller;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteResponse {

    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
}
