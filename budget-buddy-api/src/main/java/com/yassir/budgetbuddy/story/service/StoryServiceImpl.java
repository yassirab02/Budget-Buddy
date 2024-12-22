package com.yassir.budgetbuddy.story.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.reaction.ReactionType;
import com.yassir.budgetbuddy.reaction.StoryReaction;
import com.yassir.budgetbuddy.reaction.StoryReactionRepository;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.story.StoryStatus;
import com.yassir.budgetbuddy.story.repository.StoryRepository;
import com.yassir.budgetbuddy.story.controller.StoryMapper;
import com.yassir.budgetbuddy.story.controller.StoryRequest;
import com.yassir.budgetbuddy.story.controller.StoryResponse;
import com.yassir.budgetbuddy.story.repository.StorySpecification;
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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryMapper storyMapper;
    private final StoryRepository repository;
    private StoryReactionRepository storyReactionRepository;

    @Override
    public StoryResponse addOrUpdateStory(StoryRequest request,Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Story story = storyMapper.toStory(request);
        story.setOwner(user);
        repository.save(story);
        // Calculate the number of likes and dislikes dynamically
        long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);
        long dislikes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.DISLIKE);
        return storyMapper.toStoryResponse(story, likes, dislikes);
    }

    @Override
    public void deleteStory(Integer storyId) {
        boolean condition = (storyId != null);
        if (condition) {
            Story story = repository.findById(storyId)
                    .orElseThrow(() -> new EntityNotFoundException("Story not found with id: " + storyId));

            repository.delete(story);
        }
    }


    @Override
    public void hideStory(Integer storyId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        boolean condition = (storyId != null);
        if (condition) {
            Story story = repository.findById(storyId)
                    .orElseThrow(() -> new IllegalArgumentException("Story not found"));
            if (!Objects.equals(user.getId(), story.getOwner().getId())) {
                throw new IllegalArgumentException("You are not allowed to hide this story");
            }
            story.setArchived(false);
            story.setStatus(StoryStatus.ARCHIVED);
            repository.save(story);
        }
    }

    @Override
    public StoryResponse findStoryById(Integer storyId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Story story = repository.findById(storyId)
                .orElseThrow(() -> new EntityNotFoundException("Story not found with id: " + storyId));

        if (!Objects.equals(user.getId(), story.getOwner().getId())){
            throw new IllegalArgumentException("You are not allowed to see this story");
        }
        // Calculate the number of likes and dislikes dynamically
        long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);
        long dislikes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.DISLIKE);

        return storyMapper.toStoryResponse(story, likes, dislikes);
    }

    @Override
    public PageResponse<StoryResponse> findAllStoriesByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Story> Stories = repository.findAll(StorySpecification.withOwnerId(user.getId()), pageable);
        List<StoryResponse> storyResponse = Stories.stream()
                .map(storyMapper::toStoryResponse)
                .toList();
        return new PageResponse<>(
                storyResponse,
                Stories.getNumber(),
                Stories.getSize(),
                Stories.getTotalElements(),
                Stories.getTotalPages(),
                Stories.isFirst(),
                Stories.isLast()
        );
    }

    @Override
    public PageResponse<StoryResponse> findAllDisplayableStories(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Story> stories = repository.findAllDisplayableStories(pageable, user.getId());
        List<StoryResponse> storyResponse = stories.stream()
                .map(storyMapper::toStoryResponse)
                .toList();
        return new PageResponse<>(
                storyResponse,
                stories.getNumber(),
                stories.getSize(),
                stories.getTotalElements(),
                stories.getTotalPages(),
                stories.isFirst(),
                stories.isLast()
        );
    }


    @Override
    public PageResponse<StoryResponse> findAllStories(int page, int size, Authentication connectedUser) {

        if (connectedUser.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You are not allowed to see all stories");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Story> books = repository.findAll(pageable);
        List<StoryResponse> bookResponse = books.stream()
                .map(storyMapper::toStoryResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }


    public StoryResponse toggleReaction(Integer storyId, ReactionType reactionType, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Validate story existence
        Story story = repository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("Story not found with id: " + storyId));

        // Check if the user has already reacted to the story
        Optional<StoryReaction> existingReaction = storyReactionRepository.findByStoryIdAndUserId(storyId, user.getId());

        if (existingReaction.isPresent()) {
            StoryReaction reaction = existingReaction.get();

            if (reaction.getReaction() == reactionType) {
                // If the user toggles the same reaction, remove it
                storyReactionRepository.delete(reaction);
            } else {
                // Otherwise, update the reaction type
                reaction.setReaction(reactionType);
                storyReactionRepository.save(reaction);
            }
        } else {
            // Add a new reaction
            StoryReaction newReaction = new StoryReaction();
            newReaction.setStory(story);
            newReaction.setUser(user);
            newReaction.setReaction(reactionType);
            storyReactionRepository.save(newReaction);
        }

        // Fetch updated counts dynamically for likes and dislikes
        long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);
        long dislikes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.DISLIKE);

        return storyMapper.toStoryResponse(story, likes, dislikes);
    }


}
