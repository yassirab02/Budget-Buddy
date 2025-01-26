package com.yassir.budgetbuddy.story;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Story extends BaseEntity {

    private String title;

    @Lob
    private String description;

    @Lob
    private String content;

    private String cover;
    private boolean archived;
    private Long numberOfLikes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoryStatus status;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


}
