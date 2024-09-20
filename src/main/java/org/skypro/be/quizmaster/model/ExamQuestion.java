package org.skypro.be.quizmaster.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExamQuestion {
    private String textQuestion;
    private QuestionType questionType;
    private Section section;
    private Boolean correct;
    private List<Answer> correctAnswers;
    private List<Answer> userAnswers = new ArrayList<>();

    public ExamQuestion() {
    }

    public ExamQuestion(Question question) {
        this.textQuestion = question.getTextQuestion();
        this.questionType = question.getQuestionType();
        this.section = question.getSection();
        this.correct = false;
        this.correctAnswers = question.getAnswers();
        this.userAnswers = setBlankUserAnswers(question.getAnswers());
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Answer> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<Answer> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<Answer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(List<Answer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
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
