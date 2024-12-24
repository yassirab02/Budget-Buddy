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

    @GetMapping("/goals/reached")
    public ResponseEntity<PageResponse<GoalResponse>> findGoalsByUserAndReachedStatus(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "reached") boolean reached,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findGoalsByUserAndReachedStatus(page, size, connectedUser, reached));
    }


    @GetMapping("/{goal-id}")
    public ResponseEntity<GoalResponse> findGoalById(
            @PathVariable("goal-id") Integer goalId
    ) {
        return ResponseEntity.ok(service.findGoalById(goalId));
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
