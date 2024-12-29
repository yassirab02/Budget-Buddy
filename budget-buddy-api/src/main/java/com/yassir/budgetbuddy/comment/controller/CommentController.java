package com.yassir.budgetbuddy.comment.controller;

import com.yassir.budgetbuddy.comment.service.CommentService;
import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.reaction.ReactionType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Tag(name = "Comment")
public class CommentController {

    private final CommentService service;

    @PostMapping("add-comment")
    public ResponseEntity<CommentResponse> addOrUpdateComment(
            @RequestBody @Valid CommentRequest request,
            Authentication connectedUser) {

        return ResponseEntity.ok(service.addOrUpdateComment(request, connectedUser));
    }

    @PostMapping("/toggle/{comment-id}/reaction/{reactionType}")
    public ResponseEntity<CommentResponse> toggle(
            @PathVariable("comment-id") Integer commentId,
            Authentication connectedUser,
            @PathVariable ReactionType reactionType

    ) {
        return ResponseEntity.ok(service.toggleCommentReaction(commentId,reactionType,connectedUser));
    }

    @GetMapping("/story/{story-id}")
    public ResponseEntity<PageResponse<CommentResponse>> findAllCommentsByStory(
            @PathVariable("story-id") Integer storyId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllCommentsByStory(storyId,page,size,connectedUser));
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable("comment-id") Integer commentId,
            Authentication connectedUser
    ) {
        service.deleteComment(commentId, connectedUser);
        return ResponseEntity.noContent().build();
    }

}
