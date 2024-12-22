package com.yassir.budgetbuddy.comment.controller;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private Integer id;
    private String comment;
    private Long numberOfLikes;
    private Long numberOfDislikes;
    private Boolean isActive;
    private Boolean isFlagged;
    private String flaggedReason;
    private Boolean isEdited;
    private Integer storyId;
    private Integer userId;
    private Integer parentCommentId;
}
