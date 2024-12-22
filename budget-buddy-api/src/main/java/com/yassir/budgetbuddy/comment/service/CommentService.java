package com.yassir.budgetbuddy.comment.service;

import com.yassir.budgetbuddy.comment.controller.CommentRequest;
import com.yassir.budgetbuddy.comment.controller.CommentResponse;
import com.yassir.budgetbuddy.reaction.ReactionType;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;

public interface CommentService {

    // saves and returns the comment
    @Transactional
    CommentResponse addOrUpdateComment(CommentRequest request, Authentication connectedUser);

    // handles the likes / dislikes
    @Transactional
    CommentResponse toggleReaction(Integer commentId, ReactionType reactionType, Authentication connectedUser);

    void deleteComment(Integer commentId, Authentication connectedUser);
}
