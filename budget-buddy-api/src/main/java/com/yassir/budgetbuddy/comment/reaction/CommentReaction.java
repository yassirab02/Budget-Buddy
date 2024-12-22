package com.yassir.budgetbuddy.comment.reaction;

import com.yassir.budgetbuddy.comment.Comment;
import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CommentReaction {

    @EmbeddedId
    private CommentReactionId id;

    @ManyToOne
    @MapsId("commentId")
    private Comment comment;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType reaction; // Enum: LIKE, DISLIKE
}
