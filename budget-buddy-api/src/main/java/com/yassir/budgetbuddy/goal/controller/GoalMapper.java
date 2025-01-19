package com.yassir.budgetbuddy.goal.controller;


import com.yassir.budgetbuddy.goal.Goal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class GoalMapper {

    public Goal toGoal(GoalRequest request) {
        return Goal.builder()
                .id(request.id())  // Optional, for updating an existing goal
                .name(request.name())
                .description(request.description()) // Optional description
                .targetAmount(request.targetAmount())
                .currentAmount(request.currentAmount())
                .startDate(request.startDate())
                .targetDate(request.targetDate())
                .reached(request.reached())  // Assuming goal is not reached initially
                .build();
    }

    public GoalResponse toGoalResponse(Goal goal) {
        return GoalResponse.builder()
                .id(goal.getId())
                .name(goal.getName())
                .description(goal.getDescription())
                .targetAmount(goal.getTargetAmount())
                .currentAmount(goal.getCurrentAmount())
                .startDate(goal.getStartDate())
                .targetDate(goal.getTargetDate())
                .reached(goal.isReached())
                .owner(goal.getUser().fullName())
                .build();
    }

}
