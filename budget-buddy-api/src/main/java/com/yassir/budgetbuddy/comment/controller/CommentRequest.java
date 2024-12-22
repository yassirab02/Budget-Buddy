package com.yassir.budgetbuddy.comment.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
    Integer id, // Optional, null when creating a new comment

    @NotNull(message = "Comment cannot be null")
    @NotEmpty(message = "Comment cannot be empty")
    String comment,

    Long numberOfLikes, // Optional, defaults to 0 when creating a new comment
    Long numberOfDislikes, // Optional, defaults to 0 when creating a new comment

    Boolean isActive,
    Boolean isFlagged,
    String flaggedReason, // Optional field
    Boolean isEdited,

    @NotNull(message = "Story ID is required")
    Integer storyId,

    @NotNull(message = "User ID is required")
    Integer userId,

    Integer parentCommentId // Optional, null if the comment is not a reply
) {

}