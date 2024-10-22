package org.skypro.be.quizmaster.exception;

import lombok.extern.slf4j.Slf4j;
import org.skypro.be.quizmaster.service.question.QuestionService;

@Slf4j
public class InvalidQuestionServiceException extends RuntimeException {
    public InvalidQuestionServiceException(Class<? extends QuestionService> serviceClass) {
        super("Service is not annotated with @QuestionService" + serviceClass.getName());
        log.error(this.getMessage());
        throw new RuntimeException(this.getMessage());
    }
}
