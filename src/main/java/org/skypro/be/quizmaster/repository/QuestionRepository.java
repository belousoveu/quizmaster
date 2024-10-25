package org.skypro.be.quizmaster.repository;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {


    @Query("SELECT COUNT(q) FROM Question q WHERE q.section IN :sections AND q.questionType IN :questionTypes")
    long countBySectionAndQuestionType(@Param("sections") List<Section> sections,
                                       @Param("questionTypes") List<QuestionType> questionTypes);

    @Query("SELECT q.id FROM Question q WHERE q.section = :section AND q.questionType = :questionType")
    List<Long> findIdsBySectionAndType(@Param("section") Section section,
                                       @Param("questionType") QuestionType questionType);

}

