package org.skypro.be.quizmaster.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.skypro.be.quizmaster.exception.InvalidSectionException;

import java.util.Objects;

public enum Section {

    JAVA("java", "Вопросы по Java Core", false),
    SPRING("spring", "Вопросы по Spring Framework", false),
    MATH("math", "Вопросы по арифметике", true);

    private final String name;
    @Getter
    private final String description;
    private final boolean AutomaticQuestionGeneration;

    Section(String name, String description, boolean automaticQuestionGeneration) {
        this.name = name;
        this.description = description;
        this.AutomaticQuestionGeneration = automaticQuestionGeneration;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public boolean getAutomaticQuestionGeneration() {
        return AutomaticQuestionGeneration;
    }

    @JsonCreator
    public static Section getByName(String name) {
        for (Section section : Section.values()) {
            if (Objects.equals(section.getName(), name)) {
                return section;
            }
        }
        throw new InvalidSectionException("Неизвестное имя раздела: " + name);
    }

}
