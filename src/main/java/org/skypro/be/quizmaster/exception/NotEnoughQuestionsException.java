package org.skypro.be.quizmaster.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;

import java.util.List;

@Slf4j
@Getter
public class NotEnoughQuestionsException extends RuntimeException {
    private final int numberOfQuestions;
    private final List<Section> selectedSections;
    private final List<QuestionType> selectedTypes;

    public NotEnoughQuestionsException(int numberOfQuestions, List<Section> selectedSections, List<QuestionType> selectedTypes) {
        super("Not enough questions for the selected sections and types. " +
                "Number of questions: " + numberOfQuestions +
                ", Selected sections: " + selectedSections +
                ", Selected types: " + selectedTypes);
        this.numberOfQuestions = numberOfQuestions;
        this.selectedSections = selectedSections;
        this.selectedTypes = selectedTypes;
        log.warn(this.getMessage());
    }

    public Object getMessageForUser() {
        return "В базе недостаточно вопросов для проведения теста по указанным параметрам:" +
                "<br><b>Количество вопросов: </b>" + numberOfQuestions +
                "<br><b>Разделы: </b>" + selectedSections.stream().map(Section::getDescription).reduce((a, b) -> a + ", " + b).orElse("") +
                "<br><b>Типы вопросов: </b>" + selectedTypes.stream().map(QuestionType::getName).reduce((a, b) -> a + ", " + b).orElse("") +
                "<br>Уменьшите количество вопросов или расширьте список возможных разделов и типов вопросов";
    }
}
