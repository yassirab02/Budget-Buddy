package com.yassir.budgetbuddy.story.repository;

import com.yassir.budgetbuddy.story.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryRepository extends JpaRepository<Story, Integer> , JpaSpecificationExecutor<Story> {

    @Query("""
        SELECT story
        FROM Story story
        WHERE story.archived = false
        AND story.status = 'PUBLISHED'
        AND story.owner.id <> :userId
       """)
    Page<Story> findAllDisplayableStories(Pageable pageable, @Param("userId") Integer id);

}
