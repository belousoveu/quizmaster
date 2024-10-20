package org.skypro.be.quizmaster.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
    private long totalQuestions;

    @Column
    private long correctAnswers;

    public Result() {
    }

    public Result(Long userId, String description, LocalDateTime examPassed, long totalQuestions, long correctAnswers) {
        this.userId = userId;
        this.description = description;
        this.examPassed = examPassed;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
    }

}
