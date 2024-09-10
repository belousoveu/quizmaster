package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class QuestionServiceImp  {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getQuestionsBySection(String section) {
        return null;
    }
}
