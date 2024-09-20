package org.skypro.be.quizmaster.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Answer {
    private String textAnswer;
    private Boolean isCorrect = false;

    public Answer() {}

    public Answer(String textAnswer, Boolean isCorrect) {
        this.textAnswer = textAnswer;
        this.isCorrect = isCorrect;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public Boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String toString() {
        return isCorrect + ", " + textAnswer+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(textAnswer, answer.textAnswer) && Objects.equals(isCorrect, answer.isCorrect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textAnswer, isCorrect);
    }
}
