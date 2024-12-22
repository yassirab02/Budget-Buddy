package com.yassir.budgetbuddy.comment.service;

import com.yassir.budgetbuddy.comment.Comment;
import com.yassir.budgetbuddy.reaction.CommentReaction;
import com.yassir.budgetbuddy.comment.CommentRepository;
import com.yassir.budgetbuddy.comment.controller.CommentMapper;
import com.yassir.budgetbuddy.comment.controller.CommentRequest;
import com.yassir.budgetbuddy.comment.controller.CommentResponse;
import com.yassir.budgetbuddy.reaction.CommentReactionRepository;
import com.yassir.budgetbuddy.reaction.ReactionType;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.story.repository.StoryRepository;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final StoryRepository storyRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final CommentReactionRepository commentReactionRepository;

    // saves and returns the comment
    @Transactional
    @Override
    public CommentResponse addOrUpdateComment(CommentRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.userId()));

        // Validate Story existence
        Story story = storyRepository.findById(request.storyId())
                .orElseThrow(() -> new IllegalArgumentException("Story not found with id: " + request.storyId()));

        // Handle Parent Comment existence if provided
        Comment parentComment = null;
        if (request.parentCommentId() != null) {
            parentComment = repository.findById(request.parentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found with id: " + request.parentCommentId()));
            parentComment.setNumberOfReplies(parentComment.getNumberOfReplies() + 1);
        }

        // Check if the comment already exists (by id)
        Comment comment;
        boolean isNewComment = false;
        if (request.id() != null) {
            // Update existing comment
            comment = repository.findById(request.id())
                    .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + request.id()));

            // Update the fields that can be modified
            comment.setComment(request.comment());
            comment.setIsActive(request.isActive());
            comment.setIsFlagged(request.isFlagged());
            comment.setFlaggedReason(request.flaggedReason());
            comment.setIsEdited(true);  // Mark the comment as edited
        } else {
            // Create new comment
            isNewComment = true;
            comment = commentMapper.toComment(request);
        }

        // Set the necessary relationships and values
        comment.setUser(user);
        comment.setStory(story);
        comment.setParentComment(parentComment);

        // Save Comment to repository
        Comment savedComment = repository.save(comment);

        // Calculate the number of likes and dislikes dynamically
        long likes = commentReactionRepository.countReactionsByType(savedComment.getId(), ReactionType.LIKE);
        long dislikes = commentReactionRepository.countReactionsByType(savedComment.getId(), ReactionType.DISLIKE);

        // Only increment the number of comments if it's a new comment
        if (isNewComment) {
            story.setNumberOfComments(story.getNumberOfComments() + 1);
            storyRepository.save(story);
        }

        // Map saved Comment to Response
        return commentMapper.toCommentResponse(savedComment, likes, dislikes);
    }


    @Transactional
    @Override
    public CommentResponse toggleReaction(Integer commentId, ReactionType reactionType, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Validate comment existence
        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        // Check if the user has already reacted to the comment
        Optional<CommentReaction> existingReaction = commentReactionRepository.findByCommentIdAndUserId(commentId, user.getId());

        if (existingReaction.isPresent()) {
            CommentReaction reaction = existingReaction.get();

            if (reaction.getReaction() == reactionType) {
                // If the user toggles the same reaction, remove it
                commentReactionRepository.delete(reaction);
            } else {
                // Otherwise, update the reaction type
                reaction.setReaction(reactionType);
                commentReactionRepository.save(reaction);
            }
        } else {
            // Add a new reaction
            CommentReaction newReaction = new CommentReaction();
            newReaction.setComment(comment);
            newReaction.setUser(user);
            newReaction.setReaction(reactionType);
            commentReactionRepository.save(newReaction);
        }

        // Fetch updated counts dynamically for likes and dislikes
        long likes = commentReactionRepository.countReactionsByType(comment.getId(), ReactionType.LIKE);
        long dislikes = commentReactionRepository.countReactionsByType(comment.getId(), ReactionType.DISLIKE);

        return commentMapper.toCommentResponse(comment, likes, dislikes);
    }

    @Override
    public void deleteComment(Integer commentId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("No Comment found with the Id: " + commentId));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete this comment");
        }

        if (!comment.getReplies().isEmpty()) {
            deleteReplies(comment);
        }

        if (comment.getParentComment() != null) {
            Comment parentComment = repository.findById(comment.getParentComment().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found with id: " + comment.getParentComment().getId()));

            parentComment.setNumberOfReplies(parentComment.getNumberOfReplies() - 1);
            repository.save(parentComment);  // Save the parent comment after modifying the reply count
        }

        // Finally, delete the comment itself
        repository.delete(comment);
    }

    private void deleteReplies(Comment parentComment) {
        // First, delete all replies
        List<Comment> replies = parentComment.getReplies();
        for (Comment reply : replies) {
            deleteReplies(reply); // Recursively delete nested replies
            repository.delete(reply); // Delete the reply
        }
    }
}
