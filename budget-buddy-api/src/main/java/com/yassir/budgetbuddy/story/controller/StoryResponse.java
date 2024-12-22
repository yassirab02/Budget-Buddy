package com.yassir.budgetbuddy.story.controller;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryResponse {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private String cover;
    private boolean archived;
    private Long numberOfLikes;
    private Long numberOfDislikes;
    private String status;
    private String owner;
    private List<String> comments; // Can store comment details or IDs
}
