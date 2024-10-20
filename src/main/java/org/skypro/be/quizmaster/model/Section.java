package org.skypro.be.quizmaster.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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
            if (section.getName().equals(name)) {
                return section;
            }
        }
        throw new IllegalArgumentException("Неизвестное имя раздела: " + name + "\nДанная ошибка не должна появляться"); //TODO сделать отдельное исключение
    }

}
