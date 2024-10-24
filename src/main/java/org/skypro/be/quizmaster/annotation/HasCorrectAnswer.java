package org.skypro.be.quizmaster.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HasCorrectAnswerValidator.class)
public @interface HasCorrectAnswer {
    String message() default "There must be at least one correct answer in the list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
