package com.yassir.budgetbuddy.comment.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, CommentReactionId> {

    Optional<CommentReaction> findByCommentIdAndUserId(Integer commentId, Integer userId);

    @Query("SELECT COUNT(cr) FROM CommentReaction cr WHERE cr.comment.id = :commentId AND cr.reaction = :reactionType")
    long countReactionsByType(@Param("commentId") Integer commentId, @Param("reactionType") ReactionType reactionType);
}
