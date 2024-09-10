package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManualQuestionService implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

//
//    public ManualQuestionService(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }

    @Override
    public List<Question> getQuestions(Section section) {
        return questionRepository.findAll().stream()
                .filter(question -> question.getSection().equals(section)).toList();

    }

    @Override
    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

}

