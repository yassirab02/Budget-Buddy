package com.yassir.budgetbuddy.comment.service;

import com.yassir.budgetbuddy.comment.Comment;
import com.yassir.budgetbuddy.comment.CommentRepository;
import com.yassir.budgetbuddy.comment.controller.CommentMapper;
import com.yassir.budgetbuddy.comment.controller.CommentRequest;
import com.yassir.budgetbuddy.comment.controller.CommentResponse;
import com.yassir.budgetbuddy.comment.reaction.CommentReaction;
import com.yassir.budgetbuddy.comment.reaction.CommentReactionRepository;
import com.yassir.budgetbuddy.comment.reaction.ReactionType;
import com.yassir.budgetbuddy.story.Story;
import com.yassir.budgetbuddy.story.repository.StoryRepository;
import com.yassir.budgetbuddy.user.User;
import com.yassir.budgetbuddy.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final StoryRepository storyRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final CommentReactionRepository commentReactionRepository;

    // saves and returns the comment
    @Transactional
    @Override
    public CommentResponse addComment(CommentRequest request, Authentication connectedUser) {

        User user = (User) connectedUser.getPrincipal();
        // Validate User existence
        userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.userId()));

        // Validate Story existence
        Story story = storyRepository.findById(request.storyId())
                .orElseThrow(() -> new IllegalArgumentException("Story not found with id: " + request.storyId()));

        // Handle Parent Comment existence if provided
        Comment parentComment = null;
        if (request.parentCommentId() != null) {
            parentComment = commentRepository.findById(request.parentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found with id: " + request.parentCommentId()));
        }

        // Map request to Comment entity
        Comment comment = commentMapper.toComment(request);
        comment.setNumberOfLikes(0L);
        comment.setNumberOfDislikes(0L);
        comment.setStory(story);
        comment.setUser(user);
        comment.setParentComment(parentComment);

        // Save Comment to repository
        Comment savedComment = commentRepository.save(comment);

        // Map saved Comment to Response
        return commentMapper.toCommentResponse(savedComment, 0, 0);
    }

    @Transactional
    @Override
    public CommentResponse toggleReaction(Integer commentId, ReactionType reactionType, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        // Validate comment existence
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

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
            // Add new reaction
            CommentReaction newReaction = new CommentReaction();
            newReaction.setComment(comment);
            newReaction.setUser(user);
            newReaction.setReaction(reactionType);
            commentReactionRepository.save(newReaction);
        }

        // Fetch updated counts dynamically
        long likes = commentReactionRepository.countReactionsByType(comment.getId(), ReactionType.LIKE);
        long dislikes = commentReactionRepository.countReactionsByType(comment.getId(), ReactionType.DISLIKE);

        return commentMapper.toCommentResponse(comment, likes, dislikes);
    }



}
