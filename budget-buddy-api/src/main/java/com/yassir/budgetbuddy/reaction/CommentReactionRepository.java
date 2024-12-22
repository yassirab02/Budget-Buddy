package com.yassir.budgetbuddy.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Integer> {

    // Corrected method to find a CommentReaction by commentId and userId
    Optional<CommentReaction> findByCommentIdAndUserId(Integer commentId, Integer userId);

    // Query to count reactions of a specific type for a comment
    @Query("SELECT COUNT(cr) FROM CommentReaction cr WHERE cr.comment.id = :commentId AND cr.reaction = :reactionType")
    long countReactionsByType(@Param("commentId") Integer commentId, @Param("reactionType") ReactionType reactionType);
}
