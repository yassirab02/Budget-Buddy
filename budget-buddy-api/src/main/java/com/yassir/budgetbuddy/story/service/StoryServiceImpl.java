package com.yassir.budgetbuddy.story.service;

import com.yassir.budgetbuddy.cloud.MinIOInfos;
import com.yassir.budgetbuddy.cloud.MinIOService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryMapper storyMapper;
    private final StoryRepository repository;
    private final StoryReactionRepository storyReactionRepository;
    private final MinIOService minIOService;

    @Override
    public StoryResponse addOrUpdateStory(StoryRequest request,MultipartFile cover, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Story story = storyMapper.toStory(request);
        String fileName =  user.fullName()+"_"+request.title()+ ".png";
        String coverPath = uploadCover(fileName,cover);
        story.setCoverPath(coverPath);
        story.setOwner(user);
        repository.save(story);
        if (request.id() != null) {
            long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);
            return storyMapper.toStoryResponse(story, likes,false);
        }
        return storyMapper.toStoryResponse(story,0L,false);
    }

    private String uploadCover(String fileName, MultipartFile cover) {
        String bucketName = "budget-buddy";
        try {
            byte[] coverBytes = cover.getBytes();
            MinIOInfos uploadedFile = minIOService.uploadToMinio(coverBytes, fileName, bucketName);
            return uploadedFile.getLink();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception according to your application's needs
            return null;
        }
    }


    @Override
    @Transactional
    public void deleteStory(Integer storyId, Authentication connectedUser) {
        if (storyId == null) {
            throw new IllegalArgumentException("Story ID cannot be null");
        }

        User user = (User) connectedUser.getPrincipal();

        // Delete the reactions associated with the story
        storyReactionRepository.deleteByStoryId(storyId);

        Story story = repository.findById(storyId)
                .orElseThrow(() -> new EntityNotFoundException("Story not found with id: " + storyId));

        // Check if the user is allowed to delete the story
        if (!Objects.equals(user.getId(), story.getOwner().getId()) && user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You are not allowed to delete this story");
        }

        // Now delete the story
        repository.delete(story);
    }



    @Override
    public void hideStory(Integer storyId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        if (storyId != null) {
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
        boolean isLiked = false;
        Optional<StoryReaction> existingReaction = storyReactionRepository.findByStoryIdAndUserId(storyId, user.getId());
        if (existingReaction.isPresent()) {
            // Check if the reaction is a LIKE
            isLiked = existingReaction.get().getReaction() == ReactionType.LIKE;
        }

        long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);

        // Map the story to a response, passing the likes count and isLiked flag
        return storyMapper.toStoryResponse(story, likes, isLiked);
    }


    @Override
    public PageResponse<StoryResponse> findAllStoriesByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<Story> stories = repository.findAll(StorySpecification.withOwnerId(user.getId()), pageable);

        List<StoryResponse> storyResponses = stories.stream().map(story -> {
            boolean isLiked = false;
            Optional<StoryReaction> existingReaction = storyReactionRepository.findByStoryIdAndUserId(story.getId(), user.getId());
            if (existingReaction.isPresent()) {
                isLiked = existingReaction.get().getReaction() == ReactionType.LIKE;
            }

            long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);

            // Map the story to StoryResponse
            return storyMapper.toStoryResponse(story, likes, isLiked);
        }).toList();

        // Return a PageResponse with the mapped StoryResponse
        return new PageResponse<>(
                storyResponses,
                stories.getNumber(),
                stories.getSize(),
                stories.getTotalElements(),
                stories.getTotalPages(),
                stories.isFirst(),
                stories.isLast()
        );
    }

    @Override
    public PageResponse<StoryResponse> findAllDisplayableStories(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Fetch stories that are displayable, based on the user's ID
        Page<Story> stories = repository.findAllDisplayableStories(pageable, user.getId());

        // Map stories to StoryResponse including the isLiked flag and the number of likes
        List<StoryResponse> storyResponses = stories.stream().map(story -> {
            // Check if the user has liked the story
            boolean isLiked = false;
            Optional<StoryReaction> existingReaction = storyReactionRepository.findByStoryIdAndUserId(story.getId(), user.getId());
            if (existingReaction.isPresent()) {
                isLiked = existingReaction.get().getReaction() == ReactionType.LIKE;
            }

            long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);

            // Map the story to StoryResponse
            return storyMapper.toStoryResponse(story, likes, isLiked);
        }).toList();

        // Return a PageResponse with the mapped StoryResponse
        return new PageResponse<>(
                storyResponses,
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
        User user = ((User) connectedUser.getPrincipal());

        // Check if the user has ADMIN role
        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new IllegalArgumentException("You are not allowed to see all stories");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Fetch all stories
        Page<Story> stories = repository.findAll(pageable);

        // Map stories to StoryResponse including the isLiked flag and the number of likes
        List<StoryResponse> storyResponses = stories.stream().map(story -> {
            // Check if the user has liked the story
            boolean isLiked = false;
            Optional<StoryReaction> existingReaction = storyReactionRepository.findByStoryIdAndUserId(story.getId(), user.getId());
            if (existingReaction.isPresent()) {
                isLiked = existingReaction.get().getReaction() == ReactionType.LIKE;
            }

            // Calculate the number of likes
            long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);

            // Map the story to StoryResponse
            return storyMapper.toStoryResponse(story, likes, isLiked);
        }).toList();

        // Return a PageResponse with the mapped StoryResponse
        return new PageResponse<>(
                storyResponses,
                stories.getNumber(),
                stories.getSize(),
                stories.getTotalElements(),
                stories.getTotalPages(),
                stories.isFirst(),
                stories.isLast()
        );
    }


    @Override
    public StoryResponse toggleStoryReaction(Integer storyId, ReactionType reactionType, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Validate story existence
        Story story = repository.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("Story not found with id: " + storyId));

        // Check if the user has already reacted to the story
        Optional<StoryReaction> existingReaction = storyReactionRepository.findByStoryIdAndUserId(storyId, user.getId());

        boolean isLiked = false; // To track if the user liked the story

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

        // Check if the user liked the story
        Optional<StoryReaction> reactionAfterToggle = storyReactionRepository.findByStoryIdAndUserId(storyId, user.getId());
        if (reactionAfterToggle.isPresent()) {
            isLiked = reactionAfterToggle.get().getReaction() == ReactionType.LIKE;
        }

        // Fetch updated counts dynamically for likes
        long likes = storyReactionRepository.countReactionsByType(story.getId(), ReactionType.LIKE);

        story.setNumberOfLikes(likes);
        repository.save(story);

        return storyMapper.toStoryResponse(story, likes, isLiked);
    }



}
