package org.skypro.be.quizmaster.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime examPassed;

    @Column
    private Long totalQuestions;

    @Column
    private Long correctAnswers;

    public Result() {}

    public Result(Long userId, String description, LocalDateTime examPassed, Long totalQuestions, Long correctAnswers) {
        this.userId = userId;
        this.description = description;
        this.examPassed = examPassed;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getExamPassed() {
        return examPassed;
    }

    public void setExamPassed(LocalDateTime examPassed) {
        this.examPassed = examPassed;
    }

    public long getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public long getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Long correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
