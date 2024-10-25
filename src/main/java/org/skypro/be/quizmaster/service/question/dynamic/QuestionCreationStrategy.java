package org.skypro.be.quizmaster.service.question.dynamic;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;

public interface QuestionCreationStrategy {
    Question createQuestion();

    Section getSection();

    QuestionType getType();
}
