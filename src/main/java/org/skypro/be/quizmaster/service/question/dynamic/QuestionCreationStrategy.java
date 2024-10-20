package org.skypro.be.quizmaster.service.question.dynamic;

import org.skypro.be.quizmaster.model.Question;

public interface QuestionCreationStrategy {
    Question createQuestion();
}
