package com.yassir.budgetbuddy.comment.controller;

import com.yassir.budgetbuddy.comment.Comment;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

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
                .creationDate(comment.getCreatedDate() != null ? comment.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME) : null)
                .lastUpdateDate(String.valueOf(comment.getLastModifiedDate()))
                .story(comment.getStory() != null ? comment.getStory().getContent() : null)
                .user(comment.getUser() != null ? comment.getUser().fullName() : null)
                .build();
    }

    public CommentResponse toAllCommentResponse(Comment comment,Integer id){
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .numberOfLikes(comment.getNumberOfLikes())
                .numberOfDislikes(comment.getNumberOfDislikes())
                .isActive(comment.getIsActive())
                .isFlagged(comment.getIsFlagged())
                .flaggedReason(comment.getFlaggedReason())
                .isEdited(comment.getIsEdited())
                .creationDate(comment.getCreatedDate() != null ? comment.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME) : null)
                .lastUpdateDate(String.valueOf(comment.getLastModifiedDate()))
                .user(comment.getUser() != null ? comment.getUser().fullName() : null)
                .ownComment(Objects.equals(comment.getCreatedBy(),id))
                .parentComment(comment.getParentComment() != null ? comment.getParentComment().getComment() : null)
                .replies(
                        comment.getReplies() != null
                                ? comment.getReplies().stream()
                                .map(reply -> toSimpleCommentResponse(reply))
                                .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .build();
    }

    public CommentResponse toSimpleCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .numberOfLikes(comment.getNumberOfLikes())
                .numberOfDislikes(comment.getNumberOfDislikes())
                .isActive(comment.getIsActive())
                .isFlagged(comment.getIsFlagged())
                .flaggedReason(comment.getFlaggedReason())
                .isEdited(comment.getIsEdited())
                .creationDate(comment.getCreatedDate() != null ? comment.getCreatedDate().format(DateTimeFormatter.ISO_DATE_TIME) : null)
                .lastUpdateDate(String.valueOf(comment.getLastModifiedDate()))
                .user(comment.getUser() != null ? comment.getUser().fullName() : null)
                .parentComment(comment.getParentComment() != null ? comment.getParentComment().getComment() : null)
                .replies(
                        comment.getReplies() != null
                                ? comment.getReplies().stream()
                                .map(reply -> toSimpleCommentResponse(reply))
                                .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .build();
    }

}
