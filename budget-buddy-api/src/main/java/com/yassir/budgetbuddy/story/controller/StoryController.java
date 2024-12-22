package com.yassir.budgetbuddy.story.controller;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.story.service.StoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("story")
@RequiredArgsConstructor
@Tag(name = "Story")
public class StoryController {

    private final StoryService service;

    @PostMapping("/add-story")
    public ResponseEntity<Integer> addOrUpdateStory(
            @RequestBody @Valid StoryRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.addOrUpdateStory(request));
    }

    @DeleteMapping("delete/{story-id}")
    public ResponseEntity<?> deleteStory(
            @PathVariable("story-id") Integer storyId
    ) {
        service.deleteStory(storyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("hide/{story-id}")
    public ResponseEntity<?> hideStory(
            @PathVariable("story-id") Integer storyId
    ) {
        service.hideStory(storyId);
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

}
