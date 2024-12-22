package com.yassir.budgetbuddy.story.service;

import com.yassir.budgetbuddy.common.PageResponse;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.story.StoryStatus;
import com.yassir.budgetbuddy.story.repository.StoryRepository;
import com.yassir.budgetbuddy.story.controller.StoryMapper;
import com.yassir.budgetbuddy.story.controller.StoryRequest;
import com.yassir.budgetbuddy.story.controller.StoryResponse;
import com.yassir.budgetbuddy.story.repository.StorySpecification;
import com.yassir.budgetbuddy.user.User;
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
public class StoryServiceImpl implements StoryService {

    private final StoryMapper storyMapper;
    private final StoryRepository repository;

    @Override
    public Integer addOrUpdateStory(StoryRequest request) {
        Story story = storyMapper.toStory(request);
        return repository.save(story).getId();
    }

    @Override
    public void deleteStory(Integer storyId) {
        boolean condition = (storyId != null);
        if (condition) {
            repository.deleteById(storyId);
        }
    }


    @Override
    public void hideStory(Integer storyId) {
        boolean condition = (storyId != null);
        if (condition) {
            Story story = repository.findById(storyId)
                    .orElseThrow(() -> new IllegalArgumentException("Story not found"));
            story.setArchived(false);
            story.setStatus(StoryStatus.ARCHIVED);
            repository.save(story);
        }
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


}
