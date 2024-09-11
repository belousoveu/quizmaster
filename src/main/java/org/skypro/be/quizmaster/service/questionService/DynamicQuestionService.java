package org.skypro.be.quizmaster.service.questionService;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class DynamicQuestionService implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestions() {
        throw new UnsupportedOperationException();
    }

    public Question createQuestion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveQuestion(Question question) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Question getQuestion(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteQuestion(Long id) {
        throw new UnsupportedOperationException();
    }

}
