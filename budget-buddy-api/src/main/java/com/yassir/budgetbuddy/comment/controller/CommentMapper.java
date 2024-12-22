package com.yassir.budgetbuddy.comment.controller;

import com.yassir.budgetbuddy.comment.Comment;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.user.User;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    public Comment toComment(CommentRequest request) {
        return Comment.builder()
                .comment(request.comment())
                .numberOfLikes(0L)
                .numberOfDislikes(0L)
                .isActive(request.isActive())
                .isFlagged(request.isFlagged())
                .flaggedReason(request.flaggedReason())
                .isEdited(request.isEdited())
                .numberOfReplies(0L)  // This will be calculated later
                .build();
    }


    public CommentResponse toCommentResponse(Comment comment, long numberOfLikes, long numberOfDislikes) {
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .numberOfLikes(numberOfLikes) // Get this from the reaction count
                .numberOfDislikes(numberOfDislikes) // Get this from the reaction count
                .isActive(comment.getIsActive())
                .isFlagged(comment.getIsFlagged())
                .flaggedReason(comment.getFlaggedReason())
                .isEdited(comment.getIsEdited())
                .storyId(comment.getStory() != null ? comment.getStory().getId() : null)
                .userId(comment.getUser() != null ? comment.getUser().getId() : null)
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .build();
    }

}
