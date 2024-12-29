package com.yassir.budgetbuddy.comment.service;

import com.yassir.budgetbuddy.comment.controller.CommentRequest;
import com.yassir.budgetbuddy.comment.controller.CommentResponse;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.reaction.ReactionType;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;

public interface CommentService {

    // saves and returns the comment
    @Transactional
    CommentResponse addOrUpdateComment(CommentRequest request, Authentication connectedUser);

    // handles the likes / dislikes
    @Transactional
    CommentResponse toggleCommentReaction(Integer commentId, ReactionType reactionType, Authentication connectedUser);

    void deleteComment(Integer commentId, Authentication connectedUser);

    PageResponse<CommentResponse> findAllCommentsByStory(Integer storyId, int page, int size, Authentication connectedUser);
}
