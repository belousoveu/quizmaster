package org.skypro.be.quizmaster.service.question;

import org.skypro.be.quizmaster.exception.GetListDynamicQuestionsException;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public abstract class DynamicQuestionService implements QuestionService {
    private Section section;

    public DynamicQuestionService() {
    }

    public DynamicQuestionService(Section section) {
        this.section = section;
    }

    @Override
    public List<Question> getQuestions() {
        throw new GetListDynamicQuestionsException(section.getDescription());
    }

    @Override
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

    @Override
    public Map<String, String> getQuestionTypesStatistics() {
        return Stream.of(QuestionType.values())
                .collect(Collectors.toMap(QuestionType::getName, e -> "&infin;"));
    }

    public abstract Question getRandomQuestion(QuestionType questionType);

}
