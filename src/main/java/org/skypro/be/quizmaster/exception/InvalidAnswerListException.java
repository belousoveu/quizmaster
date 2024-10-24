package org.skypro.be.quizmaster.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidAnswerListException extends RuntimeException {
    public InvalidAnswerListException(String message) {
        super(message);
        throw new RuntimeException(message);
    }
}
