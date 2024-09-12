package org.skypro.be.quizmaster.model;

public enum QuestionType {
    OPEN_ANSWER("Открытые вопросы"),
    SINGLE_CHOICE("Вопросы с выбором правильного ответа"),
    MULTIPLE_CHOICE("Вопросы с множественным выбором ответа");

    private final String name;

    private QuestionType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
