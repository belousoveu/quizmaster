package org.skypro.be.quizmaster.service.question;

import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.QuestionType;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    List<Question> getQuestions();

    Question createQuestion();

    void saveQuestion(Question question);

    Question getQuestion(Long id);

    void deleteQuestion(Long id);

    Question getRandomQuestion(QuestionType questionType);

    Map<String, String> getQuestionTypesStatistics();
}
