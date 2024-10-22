package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.*;
import org.skypro.be.quizmaster.service.question.QuestionService;
import org.skypro.be.quizmaster.service.question.dynamic.MathQuestionService;
import org.skypro.be.quizmaster.service.question.manual.JavaQuestionService;
import org.skypro.be.quizmaster.service.question.manual.SpringQuestionService;

import java.util.List;
import java.util.Map;

public class TestData {
    public static final Answer CORRECT_ANSWER = new Answer("Correct Answer", true);
    public static final Answer INCORRECT_ANSWER = new Answer("Incorrect Answer ", false);

    public static final Question JAVA_QUESTION = new Question(Section.JAVA);
    public static final Question SPRING_QUESTION = new Question(Section.SPRING);
    public static final Question MATH_QUESTION = new Question(Section.MATH);

    public static final User USER1 = new User();
    public static final User USER2 = new User();
    public static final User ADMIN = new User();
    public static final List<User> USERS = List.of(USER1, USER2);

    public static final String QUESTION_TEXT = "Question Text";

    public static final Map<Section, QuestionService> QUESTION_SERVICES = Map.of(
            Section.JAVA, new JavaQuestionService(),
            Section.SPRING, new SpringQuestionService(),
            Section.MATH, new MathQuestionService());


    static {
        USER1.setId(1L);
        USER1.setUsername("user1");
        USER1.setPassword("password");
        USER2.setId(2L);
        USER2.setUsername("user2");
        USER2.setPassword("password");
        ADMIN.setId(3L);
        ADMIN.setUsername("root");
    }

    public static Question getMockQuestion(Section section, QuestionType type) {
        return switch (type) {
            case OPEN_QUESTION -> getMockOpenQuestion(section);
            case SINGLE_CHOICE -> getMockSingleChoiceQuestion(section);
            case MULTIPLE_CHOICE -> getMockMultipleChoiceQuestion(section);
        };

    }


    private static Question getMockOpenQuestion(Section section) {
        Question question = new Question(section);
        question.setTextQuestion(QUESTION_TEXT);
        question.setQuestionType(QuestionType.OPEN_QUESTION);
        question.setAnswers(List.of(CORRECT_ANSWER));
        return question;
    }

    private static Question getMockSingleChoiceQuestion(Section section) {
        Question question = new Question(section);
        question.setTextQuestion(QUESTION_TEXT);
        question.setQuestionType(QuestionType.SINGLE_CHOICE);
        question.setAnswers(List.of(CORRECT_ANSWER, INCORRECT_ANSWER, INCORRECT_ANSWER, INCORRECT_ANSWER));
        return question;
    }

    private static Question getMockMultipleChoiceQuestion(Section section) {
        Question question = new Question(section);
        question.setTextQuestion(QUESTION_TEXT);
        question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        question.setAnswers(List.of(CORRECT_ANSWER, CORRECT_ANSWER, INCORRECT_ANSWER, INCORRECT_ANSWER));
        return question;
    }


    public static ExamQuestion getMockExamQuestion(Section section, QuestionType type, boolean correct) {
        ExamQuestion question = switch (type) {
            case OPEN_QUESTION -> getMockExamOpenQuestion(section);
            case SINGLE_CHOICE -> getMockExamSingleChoiceQuestion(section);
            case MULTIPLE_CHOICE -> getMockExamMultipleChoiceQuestion(section);
        };

        question.setCorrect(correct);

        return question;
    }

    private static ExamQuestion getMockExamOpenQuestion(Section section) {
        return new ExamQuestion(getMockOpenQuestion(section));
    }

    private static ExamQuestion getMockExamSingleChoiceQuestion(Section section) {
        return new ExamQuestion(getMockSingleChoiceQuestion(section));
    }

    private static ExamQuestion getMockExamMultipleChoiceQuestion(Section section) {
        return new ExamQuestion(getMockMultipleChoiceQuestion(section));
    }

}
