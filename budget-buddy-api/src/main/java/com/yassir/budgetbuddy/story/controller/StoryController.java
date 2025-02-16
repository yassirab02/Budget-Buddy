package com.yassir.budgetbuddy.story.controller;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.reaction.ReactionType;
import com.yassir.budgetbuddy.story.service.StoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("story")
@RequiredArgsConstructor
@Tag(name = "Story")
public class StoryController {

    private final StoryService service;

    @PostMapping(value = "/add-story", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StoryResponse> addOrUpdateStory(
            @RequestPart("request") @Valid StoryRequest request,
            @RequestPart("file") MultipartFile cover,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.addOrUpdateStory(request, cover, connectedUser));
    }


    @GetMapping("/{story-id}")
    public ResponseEntity<StoryResponse> findStoryById(
            @PathVariable("story-id") Integer storyId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findStoryById(storyId,connectedUser));
    }

    @PostMapping("/toggle/{story-id}/reaction/{reactionType}")
    public ResponseEntity<StoryResponse> toggle(
            @PathVariable("story-id") Integer storyId,
            Authentication connectedUser,
            @PathVariable ReactionType reactionType) {

        StoryResponse storyResponse = service.toggleStoryReaction(storyId, reactionType, connectedUser);
        return ResponseEntity.ok(storyResponse);
    }


    @PostMapping("hide/{story-id}")
    public ResponseEntity<?> hideStory(
            @PathVariable("story-id") Integer storyId,
            Authentication connectedUser
    ) {
        service.hideStory(storyId,connectedUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<StoryResponse>> findAllDisplayableStories(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllDisplayableStories(page, size, connectedUser));
    }

    @GetMapping("/all-stories")
    public ResponseEntity<PageResponse<StoryResponse>> findAllStoriesByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllStoriesByOwner(page, size,connectedUser));
    }

    @DeleteMapping("/{story-id}")
    public ResponseEntity<?> deleteStory(
            @PathVariable("story-id") Integer storyId,
            Authentication connectedUser
    ) {
        service.deleteStory(storyId,connectedUser);
        return ResponseEntity.noContent().build();
    }

}
