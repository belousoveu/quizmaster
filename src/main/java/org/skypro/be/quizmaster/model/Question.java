package org.skypro.be.quizmaster.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.skypro.be.quizmaster.exception.InvalidAnswerListException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Entity
public class Question {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column
    private String textQuestion;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private Section section;

    @Setter
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

    public List<Answer> getAnswers() {
        return answers.stream().toList();
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
        if (answers.size() == 1) {
            setQuestionType(QuestionType.OPEN_QUESTION);
        } else if (answers.stream().filter(Answer::isCorrect).count() == 1) {
            setQuestionType(QuestionType.SINGLE_CHOICE);
        } else if (answers.stream().filter(Answer::isCorrect).count() > 1) {
            setQuestionType(QuestionType.MULTIPLE_CHOICE);
        } else {
            throw new InvalidAnswerListException("Question type is unknown. Question: " + this);
        }
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
