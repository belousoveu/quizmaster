package org.skypro.be.quizmaster.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long idQuestion) {
        super("Question not found: " + idQuestion);
        log.warn(this.getMessage());
    }
}
