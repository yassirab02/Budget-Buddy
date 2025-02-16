package com.yassir.budgetbuddy.story.controller;

import lombok.*;

import java.time.LocalDateTime;
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
    private String coverPath;
    private boolean archived;
    private Long numberOfLikes;
    private boolean isLiked;  // Add the liked property
    private String status;
    private LocalDateTime createdDate;
    private String owner;
}
