package com.yassir.budgetbuddy.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
