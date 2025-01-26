package com.yassir.budgetbuddy.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StoryReactionRepository extends JpaRepository<StoryReaction, Integer> {

    Optional<StoryReaction> findByStoryIdAndUserId(Integer storyId, Integer userId);

    List<StoryReaction> findByUserIdAndStoryIdIn(Integer userId, List<Integer> storyIds);


    @Query("SELECT COUNT(sr) FROM StoryReaction sr WHERE sr.story.id = :storyId AND sr.reaction = :reactionType")
    long countReactionsByType(@Param("storyId") Integer storyId, @Param("reactionType") ReactionType reactionType);

    @Query("SELECT r.story.id, COUNT(r) FROM StoryReaction r WHERE r.story.id IN :storyIds AND r.reaction = :reactionType GROUP BY r.story.id")
    Map<Integer, Long> countReactionsByTypeForStories(@Param("storyIds") List<Integer> storyIds, @Param("reactionType") ReactionType reactionType);

}
