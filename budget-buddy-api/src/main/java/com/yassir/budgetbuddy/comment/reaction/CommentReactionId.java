package com.yassir.budgetbuddy.comment.reaction;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class CommentReactionId implements Serializable {

    // Getters and setters
    private Long commentId;
    private Long userId;

    // Default constructor
    public CommentReactionId() {}

    // Parameterized constructor
    public CommentReactionId(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

    // Override equals() and hashCode() for composite key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentReactionId that = (CommentReactionId) o;
        return Objects.equals(commentId, that.commentId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, userId);
    }
}
