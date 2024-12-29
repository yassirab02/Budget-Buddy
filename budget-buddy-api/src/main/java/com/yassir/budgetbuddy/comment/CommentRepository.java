package com.yassir.budgetbuddy.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("""
            SELECT comment
            FROM Comment comment
            WHERE comment.story.id = :storyId
           """)
    Page<Comment> findAllByStoryId(Integer storyId, Pageable pageable);
}
