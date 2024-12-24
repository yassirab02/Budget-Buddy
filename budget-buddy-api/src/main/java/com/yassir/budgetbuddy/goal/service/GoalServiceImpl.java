package com.yassir.budgetbuddy.goal.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.goal.Goal;
import com.yassir.budgetbuddy.goal.repository.GoalRepository;
import com.yassir.budgetbuddy.goal.controller.GoalMapper;
import com.yassir.budgetbuddy.goal.controller.GoalRequest;
import com.yassir.budgetbuddy.goal.controller.GoalResponse;
import com.yassir.budgetbuddy.goal.repository.GoalSpecification;
import com.yassir.budgetbuddy.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService{

    private final GoalRepository repository;
    private final GoalMapper goalMapper;

    @Override
    public Integer addOrUpdateBudget(GoalRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Goal goal = goalMapper.toGoal(request);
        goal.setUser(user);
        return repository.save(goal).getId();
    }


    // find all goals by user
    @Override
    public PageResponse<GoalResponse> findAllGoalsByUser(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdDate").descending());
        Page<Goal> goals = repository.findAll(GoalSpecification.withUserId(user.getId()), pageable);
        List<GoalResponse> goalResponse = goals.stream()
                .map(goalMapper::toGoalResponse)
                .toList();
        return new PageResponse<>(
                goalResponse,
                goals.getNumber(),
                goals.getSize(),
                goals.getTotalElements(),
                goals.getTotalPages(),
                goals.isFirst(),
                goals.isLast()
        );
    }


    @Override
    public PageResponse<GoalResponse> findGoalsByUserAndReachedStatus(int page, int size, Authentication connectedUser, boolean reached) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());  // Create pageable for pagination
        Page<Goal> goals = repository.findGoalsByUserAndReachedStatus(user.getId(), reached, pageable);  // Fetch goals based on reached status
        List<GoalResponse> goalResponse = goals.stream()
                .map(goalMapper::toGoalResponse)
                .toList();

        return new PageResponse<>(
                goalResponse,
                goals.getNumber(),
                goals.getSize(),
                goals.getTotalElements(),
                goals.getTotalPages(),
                goals.isFirst(),
                goals.isLast()
        );
    }


    @Override
    public void deleteGoal(Integer goalId, Authentication connectedUser) {
        Goal goal = repository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("No Budget found with the Id : " + goalId));
        User user = ((User) connectedUser.getPrincipal());

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this Budget");
        }

        repository.delete(goal);
    }

    @Override
    public GoalResponse findGoalById(Integer goalId) {
        Goal goal = repository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("No Goal found with the Id : " + goalId));
        return goalMapper.toGoalResponse(goal);
    }

}
