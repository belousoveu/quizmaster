package org.skypro.be.quizmaster.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Embeddable
public class Answer {
    private String textAnswer;
    private Boolean isCorrect = false;

    public Answer() {
    }

    public Answer(String textAnswer, Boolean isCorrect) {
        this.textAnswer = textAnswer;
        this.isCorrect = isCorrect;
    }

    public Boolean isCorrect() {
        return isCorrect;
    }

    public String toString() {
        return isCorrect + ", " + textAnswer + "\n";
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
