package org.skypro.be.quizmaster.annotation;

import org.skypro.be.quizmaster.model.Section;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QuestionServiceSection {
    Section value();
}
