package com.yassir.budgetbuddy.comment.controller;

import com.yassir.budgetbuddy.budget.controller.BudgetRequest;
import com.yassir.budgetbuddy.comment.Comment;
import com.yassir.budgetbuddy.comment.service.CommentService;
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

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<?> deleteComment(
            @PathVariable("comment-id") Integer commentId,
            Authentication connectedUser
    ) {
        service.deleteComment(commentId, connectedUser);
        return ResponseEntity.noContent().build();
    }

}
