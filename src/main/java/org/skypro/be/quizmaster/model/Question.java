package org.skypro.be.quizmaster.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    @Enumerated(EnumType.STRING)
    private Section section;

    @Column
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

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
        if (answers.size() == 1) {
            setQuestionType(QuestionType.OPEN_QUESTION);
        } else if (answers.stream().filter(Answer::isCorrect).count() == 1) {
            setQuestionType(QuestionType.SINGLE_CHOICE);
        } else if (answers.stream().filter(Answer::isCorrect).count() > 1) {
            setQuestionType(QuestionType.MULTIPLE_CHOICE);
        }
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return section.getName() + " " + textQuestion + "\n" + answers.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(textQuestion, question.textQuestion) && section == question.section;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textQuestion, section);
    }
}
