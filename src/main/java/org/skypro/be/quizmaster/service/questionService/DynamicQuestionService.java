package org.skypro.be.quizmaster.service.questionService;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public abstract class DynamicQuestionService implements QuestionService{
    private final boolean AutomaticQuestionGenerated = true;
    protected Section section;

    @Autowired
    private QuestionRepository questionRepository;

    public DynamicQuestionService() {}

    public DynamicQuestionService(Section section) {
        this.section = section;
    }

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

    public abstract Question getRandomQuestion(List<QuestionType> types);

}
