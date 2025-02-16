package com.yassir.budgetbuddy.story.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.reaction.ReactionType;
import com.yassir.budgetbuddy.story.controller.StoryRequest;
import com.yassir.budgetbuddy.story.controller.StoryResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface StoryService {

    StoryResponse addOrUpdateStory(@Valid StoryRequest request, MultipartFile cover, Authentication connectedUser);

    void deleteStory(Integer storyId,Authentication connectedUser);

    StoryResponse findStoryById(Integer storyId, Authentication connectedUser);

    PageResponse<StoryResponse> findAllStoriesByOwner(int page, int size, Authentication connectedUser);

    PageResponse<StoryResponse> findAllDisplayableStories(int page, int size, Authentication connectedUser);

    PageResponse<StoryResponse> findAllStories(int page, int size, Authentication connectedUser);

    void hideStory(Integer storyId, Authentication connectedUser);

    StoryResponse toggleStoryReaction(Integer storyId, ReactionType reactionType, Authentication connectedUser);
}
