package org.skypro.be.quizmaster.repository;

import org.skypro.be.quizmaster.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultsRepository extends JpaRepository<Result, Long> {

    @Query("Select r from Result r where r.userId = :userId")
    List<Result> findAllByUserId(@Param("userId") Long userId);
}
