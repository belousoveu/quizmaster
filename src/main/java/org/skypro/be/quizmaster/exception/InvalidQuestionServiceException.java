package org.skypro.be.quizmaster.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidQuestionServiceException extends RuntimeException {
    public InvalidQuestionServiceException(String message) {
        super(message);
        log.error(message);
        throw new RuntimeException(message);
    }
}
