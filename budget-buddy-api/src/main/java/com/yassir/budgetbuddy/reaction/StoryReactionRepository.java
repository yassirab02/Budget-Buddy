package com.yassir.budgetbuddy.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoryReactionRepository extends JpaRepository<StoryReaction, Integer> {

    Optional<StoryReaction> findByStoryIdAndUserId(Integer storyId, Integer userId);

    @Query("SELECT COUNT(sr) FROM StoryReaction sr WHERE sr.story.id = :storyId AND sr.reaction = :reactionType")
    long countReactionsByType(@Param("storyId") Integer storyId, @Param("reactionType") ReactionType reactionType);
}
