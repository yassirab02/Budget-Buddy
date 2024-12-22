package com.yassir.budgetbuddy.questions.question;

import com.yassir.budgetbuddy.common.BaseEntity;
import com.yassir.budgetbuddy.questions.answer.Answer;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity {

    private String question;

    @OneToMany(mappedBy = "question")
    List<Answer> answers;

}
