package com.yassir.budgetbuddy.story.controller;

import com.yassir.budgetbuddy.story.StoryStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record StoryRequest(

        Integer id, // Optional, null when creating a new story
        @NotNull(message = "Title is required")
        @NotEmpty(message = "Title cannot be empty")
        String title,

        String description, // Optional, null if not provided

        @NotNull(message = "Content is required")
        @NotEmpty(message = "Content cannot be empty")
        String content,

        String cover, // Optional, null if not provided

        @NotNull(message = "Archived status is required")
        Boolean archived,

        StoryStatus status

) {
}
