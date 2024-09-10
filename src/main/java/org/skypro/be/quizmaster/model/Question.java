package org.skypro.be.quizmaster.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String textQuestion;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();

    @Column
    private Section section;

    public Question() {
    }

    public Question(Section section) {
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
        this.textQuestion = textQuestion;
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
