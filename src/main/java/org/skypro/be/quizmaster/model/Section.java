package org.skypro.be.quizmaster.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.skypro.be.quizmaster.service.ManualQuestionService;
import org.skypro.be.quizmaster.service.MathQuestionService;
import org.skypro.be.quizmaster.service.QuestionService;

public enum Section {
    JAVA("java", "Вопросы по Java Core") {
    },
    SPRING("spring", "Вопросы по Spring Framework") {
    },
    MATH("math", "Вопросы по арифметике") {
        @Override
        public QuestionService getService() {
            super.isAutomaticQuestionGenerated = true;
            return new MathQuestionService();
        }
    };

    private final String name;
    private final String description;
    private Boolean isAutomaticQuestionGenerated = false;

    Section(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isAutomaticQuestionGenerated() {
        return isAutomaticQuestionGenerated;
    }

    public QuestionService getService() {
        return new ManualQuestionService();
    }

    @JsonCreator
    public static Section getByName(String name) {
        for (Section section : Section.values()) {
            if (section.getName().equals(name)) {
                return section;
            }
        }
        throw new IllegalArgumentException("Неизвестное имя раздела: " + name);
    }
}
