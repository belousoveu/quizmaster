package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class ManualQuestionService implements QuestionService {
    private final boolean AutomaticQuestionGenerated = false;
    protected Section section;

    @Autowired
    private QuestionRepository questionRepository;

    public ManualQuestionService() {
    }

    public ManualQuestionService(Section section) {
        this.section = section;
    }

    @Override
    public Question createQuestion() {
        return new Question(section);
    }

    @Override
    public List<Question> getQuestions() {
        return questionRepository.findAll().stream()
                .filter(question -> question.getSection().equals(section)).toList();

    }

    @Override
    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

}

