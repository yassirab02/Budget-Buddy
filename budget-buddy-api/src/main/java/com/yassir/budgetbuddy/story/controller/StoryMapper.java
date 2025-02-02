package com.yassir.budgetbuddy.story.controller;

import com.yassir.budgetbuddy.reaction.StoryReactionRepository;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.story.StoryStatus;
import org.springframework.stereotype.Service;

@Service
public class StoryMapper {

    public Story toStory(StoryRequest request) {
        return Story.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .content(request.content())
                .cover(request.cover())
                .archived(request.archived())
                .status(StoryStatus.PUBLISHED) // Default status
                .build();
    }

    public StoryResponse toStoryResponse(Story story, long numberOfLikes , boolean isLiked) {
        return StoryResponse.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .content(story.getContent())
                .cover(story.getCover())
                .archived(story.isArchived())
                .numberOfLikes(numberOfLikes) // Get this from the reaction count
                .isLiked(isLiked) // Get this from the reaction count
                .status(story.getStatus().name())
                .createdDate(story.getCreatedDate())
                .owner(story.getOwner().fullName()) // Assuming User has a getUsername() method
                .build();
    }

}
