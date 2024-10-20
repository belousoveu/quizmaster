package org.skypro.be.quizmaster.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.skypro.be.quizmaster.model.Answer;

import java.util.List;

public class HasCorrectAnswerValidator implements ConstraintValidator<HasCorrectAnswer, List<Answer>> {

    @Override
    public boolean isValid(List<Answer> answers, ConstraintValidatorContext context) {
        if (answers == null) {
            return false;
        }
        return answers.stream().anyMatch(Answer::isCorrect);
    }
}
