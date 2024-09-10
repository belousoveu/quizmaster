package org.skypro.be.quizmaster.repository;

import org.skypro.be.quizmaster.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
