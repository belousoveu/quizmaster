package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class DynamicQuestionService implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestions(Section section) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addQuestion(Question question) {
        throw new UnsupportedOperationException();
    }

}
