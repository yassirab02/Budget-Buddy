package com.yassir.budgetbuddy.comment.controller;

import lombok.*;

import java.util.List;

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
    private String story; // the story that the comment is related to
    private String user;
    private String creationDate;
    private String lastUpdateDate;
    private String parentComment;
    private List<CommentResponse> replies; // New field for replies comments
    private boolean ownComment; // New field to check if the comment is the user's own comment


}
