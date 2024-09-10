package org.skypro.be.quizmaster.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Answer {
    private String textAnswer;
    private Boolean isCorrect = false;

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
}
