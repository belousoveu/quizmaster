package org.skypro.be.quizmaster.model;

import lombok.Getter;

@Getter
public enum QuestionType {
    OPEN_QUESTION("Открытые вопросы"),
    SINGLE_CHOICE("Вопросы с выбором правильного ответа"),
    MULTIPLE_CHOICE("Вопросы с множественным выбором ответа");

    private final String name;

    QuestionType(String name) {
        this.name = name;
    }

}
