package com.yassir.budgetbuddy.story.controller;

import com.yassir.budgetbuddy.comment.Comment;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.user.User;
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
                .status(request.status())
                .owner(User.builder()
                        .id(request.ownerId())
                        .build())
                .build();
    }

    public StoryResponse toStoryResponse(Story story) {
        return StoryResponse.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .content(story.getContent())
                .cover(story.getCover())
                .archived(story.isArchived())
                .numberOfLikes(story.getNumberOfLikes())
                .numberOfDislikes(story.getNumberOfDislikes())
                .status(story.getStatus().name())
                .owner(story.getOwner().fullName()) // Assuming User has a getUsername() method
                .comments(story.getComments().stream()
                        .map(Comment::getComment) // Assuming Comment has a getContent() method Or .map(comment -> comment.getComment())
                        .toList())
                .build();
    }

}
