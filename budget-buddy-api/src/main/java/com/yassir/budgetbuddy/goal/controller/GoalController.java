package com.yassir.budgetbuddy.goal.controller;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.goal.service.GoalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goal")
@RequiredArgsConstructor
@Tag(name = "Goal")
public class GoalController {

    private final GoalService service;

    @PostMapping("add-goal")
    public ResponseEntity<Integer> addOrUpdateGoal(
            @RequestBody @Valid GoalRequest request,
            Authentication connectedUser) {

        return ResponseEntity.ok(service.addOrUpdateBudget(request, connectedUser));
    }

    @GetMapping(("/all-goals"))
    public ResponseEntity<PageResponse<GoalResponse>> findAllGoalsByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllGoalsByUser(page, size, connectedUser));
    }

    @GetMapping(("/reached"))
    public ResponseEntity<PageResponse<GoalResponse>> findReachedByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findReachedGoalsByUser(page, size, connectedUser));
    }

    @GetMapping(("/non-reached"))
    public ResponseEntity<PageResponse<GoalResponse>> findNonReachedByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findNonReachedGoalsByUser(page, size, connectedUser));
    }

    @DeleteMapping("/{goal-id}")
    public ResponseEntity<?> deleteBudget(
            @PathVariable("goal-id") Integer goalId,
            Authentication connectedUser
    ) {
        service.deleteGoal(goalId, connectedUser);
        return ResponseEntity.noContent().build();
    }
}
