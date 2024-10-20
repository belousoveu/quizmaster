package org.skypro.be.quizmaster.service.question;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.skypro.be.quizmaster.repository.QuestionRepository;
import org.skypro.be.quizmaster.service.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public abstract class ManualQuestionService implements QuestionService {
    private Section section;

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
        Long randomId = RandomUtils.getRandomElement(ids);
        return questionRepository.findById(randomId).orElseThrow(() -> new IllegalArgumentException("Question not found"));
    }

    @Override
    public Map<String, String> getQuestionTypesStatistics() {
        return Stream.of(QuestionType.values())
                .collect(Collectors
                        .toMap(QuestionType::getName, questionType -> String.valueOf(
                                questionRepository.countBySectionAndQuestionType(List.of(section), List.of(questionType))))
                );
    }
}
