package com.yassir.budgetbuddy.questions.answer;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.questions.question.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends BaseEntity {

    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

}
