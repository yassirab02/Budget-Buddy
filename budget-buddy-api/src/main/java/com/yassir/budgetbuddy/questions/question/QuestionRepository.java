package com.yassir.budgetbuddy.questions.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

    Optional<Question> findByQuestion(String question);

}
