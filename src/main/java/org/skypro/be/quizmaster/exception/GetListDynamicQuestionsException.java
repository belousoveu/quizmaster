package org.skypro.be.quizmaster.exception;

public class GetListDynamicQuestionsException extends UnsupportedOperationException {
    public GetListDynamicQuestionsException(String message) {
        super(String.format("Для раздела '%s' нет готовых вопросов.<br> Они формируются автоматически.", message));
    }
}
