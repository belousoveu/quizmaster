package org.skypro.be.quizmaster.service.questionService;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.skypro.be.quizmaster.service.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class ManualQuestionService implements QuestionService {
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
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question getQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question getRandomQuestion(QuestionType questionType) {
        List<Long> ids = questionRepository.findIdsBySectionAndType(section, questionType);
        Long randomId= RandomUtils.getRandomElement(ids);
        return questionRepository.findById(randomId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }
}

