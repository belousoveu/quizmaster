package org.skypro.be.quizmaster.model.dto;

import jakarta.validation.constraints.NotNull;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Section;

import java.util.ArrayList;
import java.util.List;


public class QuestionDto {


    private Long id;

    @NotNull(message = "Необходимо заполнить поле")
    private String textQuestion;

    private List<Answer> answers = new ArrayList<>();

    @NotNull(message = "Необходимо заполнить поле")
    private Section section;

    public QuestionDto() {
    }

    public QuestionDto(Section section) {
        this.textQuestion = "";
        this.answers = new ArrayList<>();
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {

        this.textQuestion = textQuestion.replaceAll("\n","<br>");
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String toString() {
        return section.getName() + " " + textQuestion + "\n" + answers.toString();

    }
}
