package org.skypro.be.quizmaster.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class ExamQuestion {
    private String textQuestion;
    private QuestionType questionType;
    private Section section;
    private Boolean correct;
    private List<Answer> correctAnswers;
    private List<Answer> userAnswers = new ArrayList<>();

    public ExamQuestion(Question question) {
        this.textQuestion = question.getTextQuestion();
        this.questionType = question.getQuestionType();
        this.section = question.getSection();
        this.correct = false;
        this.correctAnswers = question.getAnswers();
        this.userAnswers = setBlankUserAnswers(question.getAnswers());
    }

    public void updateUserAnswers(List<String> answers) {
        if (answers == null) {
            return;
        }
        switch (questionType) {
            case OPEN_QUESTION: {
                this.userAnswers.get(0).setTextAnswer(answers.get(0));
                break;
            }
            case SINGLE_CHOICE: {
                this.userAnswers.stream().filter(answer -> answer.getTextAnswer().equals(answers.get(0)))
                        .findFirst().get().setIsCorrect(true);
                break;
            }
            case MULTIPLE_CHOICE: {
                this.userAnswers.stream().filter(answer -> answers.contains(answer.getTextAnswer()))
                        .forEach(answer -> answer.setIsCorrect(true));
            }
        }
    }

    public void validateAnswer() {
        switch (questionType) {
            case OPEN_QUESTION: {
                this.correct = this.userAnswers.get(0).getTextAnswer().equals(this.correctAnswers.get(0).getTextAnswer());
                break;
            }
            case SINGLE_CHOICE:
            case MULTIPLE_CHOICE: {
                this.correct = this.userAnswers.equals(this.correctAnswers);
            }
        }
    }

    private List<Answer> setBlankUserAnswers(List<Answer> answers) {
        if (answers.size() == 1) {
            Answer singleAnswer = new Answer("", false);
            userAnswers.add(singleAnswer);
        } else {
            userAnswers = answers.stream().map(answer -> new Answer(answer.getTextAnswer(), false)).collect(Collectors.toList());
        }
        return userAnswers;
    }
}
