package com.yassir.budgetbuddy.comment.controller;

import com.yassir.budgetbuddy.comment.Comment;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.user.User;
import org.springframework.stereotype.Service;

@Service
public class CommentMapper {

    public Comment toComment(CommentRequest request) {
        return Comment.builder()
                .id(request.id())
                .comment(request.comment())
                .numberOfLikes(request.numberOfLikes())
                .numberOfDislikes(request.numberOfDislikes())
                .isActive(request.isActive())
                .isFlagged(request.isFlagged())
                .flaggedReason(request.flaggedReason())
                .isEdited(request.isEdited())
                .story(Story.builder()
                        .id(request.storyId())
                        .build())
                .user(User.builder()
                        .id(request.userId())
                        .build())
                .parentComment(request.parentCommentId() != null ? Comment.builder()
                        .id(request.parentCommentId())
                        .build() : null)
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
