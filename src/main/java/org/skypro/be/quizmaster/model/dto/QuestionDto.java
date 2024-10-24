package org.skypro.be.quizmaster.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.skypro.be.quizmaster.annotation.HasCorrectAnswer;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Section;

import java.util.ArrayList;
import java.util.List;


@Getter
public class QuestionDto {

    @Setter
    private long id;

    @NotNull(message = "Необходимо заполнить поле")
    @NotEmpty(message = "Необходимо заполнить поле")
    private String textQuestion;

    @Setter
    @NotNull(message = "Список ответов не должен быть пустым")
    @HasCorrectAnswer(message = "В списке ответов должен быть как минимум один правильный")
    private List<Answer> answers = new ArrayList<>();

    @Setter
    @NotNull(message = "Необходимо заполнить поле")
    private Section section;

    public QuestionDto() {
    }

    public QuestionDto(Section section) {
        this.textQuestion = "";
        this.answers = new ArrayList<>();
        this.section = section;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion.replaceAll("\n", "<br>");
    }

    public String toString() {
        return section.getName() + " " + textQuestion + "\n" + answers.toString();

    }
}
