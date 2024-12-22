    package com.yassir.budgetbuddy.comment;

    import com.yassir.budgetbuddy.common.BaseEntity;
    import com.yassir.budgetbuddy.story.Story;
    import com.yassir.budgetbuddy.user.User;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import lombok.experimental.SuperBuilder;

    import java.util.List;
    import java.util.Set;


    @Entity
    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public class Comment extends BaseEntity {

        @Lob
        private String comment;
        private Long numberOfReplies;
        private Long numberOfLikes;
        private Long numberOfDislikes;
        private Boolean isActive;  // This can track whether the comment is visible or deleted.
        private Boolean isFlagged; // This can track whether the comment is flagged or not.
        private String flaggedReason; // stores the reason for flagging the comment.
        private Boolean isEdited;


        @ManyToOne
        @JoinColumn(name = "story_id")
        private Story story;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToMany
        @JoinTable(
                name = "comment_likes",
                joinColumns = @JoinColumn(name = "comment_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id")
        )
        private Set<User> usersLiked;

        @ManyToMany
        @JoinTable(
                name = "comment_dislikes",
                joinColumns = @JoinColumn(name = "comment_id"),
                inverseJoinColumns = @JoinColumn(name = "user_id")
        )
        private Set<User> usersDisliked;


        @ManyToOne
        @JoinColumn(name = "parent_comment_id")
        private Comment parentComment;  // If this is a reply, this field tracks the parent comment.

        @OneToMany(mappedBy = "parentComment")
        private List<Comment> replies;  // The list of replies to this comment.

    }
