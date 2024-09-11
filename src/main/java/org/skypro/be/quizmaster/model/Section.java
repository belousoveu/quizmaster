package org.skypro.be.quizmaster.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Section {

    JAVA("java", "Вопросы по Java Core") ,
    SPRING("spring", "Вопросы по Spring Framework") ,
    MATH("math", "Вопросы по арифметике") ;

    private final String name;
    private final String description;

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
