package org.skypro.be.quizmaster.service.questionService;

import jakarta.annotation.PostConstruct;
import org.skypro.be.quizmaster.annotation.QuestionServiceSection;
import org.skypro.be.quizmaster.model.Answer;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.Section;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@QuestionServiceSection(Section.JAVA)
public class JavaQuestionService extends ManualQuestionService {
    public JavaQuestionService() {
        super(Section.JAVA);
    }

//    @PostConstruct
//    public void init() {
//        Answer answer1 = new Answer();
//        answer1.setTextAnswer("Answer 1");
//        answer1.setIsCorrect(true);
//        Answer answer2 = new Answer();
//        answer2.setTextAnswer("Answer 2");
//        answer2.setIsCorrect(false);
//        Answer answer3 = new Answer();
//        answer3.setTextAnswer("Answer 3");
//        answer3.setIsCorrect(true);
//        Question question1 = new Question();
//        question1.setTextQuestion("Java 1");
//        question1.setAnswers(List.of(answer1));
//        question1.setSection(Section.JAVA);
//        super.saveQuestion(question1);
//        Question question2 = new Question();
//        question2.setTextQuestion("Java 2");
//        question2.setAnswers(List.of(answer2, answer3));
//        question2.setSection(Section.JAVA);
//        super.saveQuestion(question2);
//        Question question3 = new Question();
//        question3.setTextQuestion("Java 3");
//        question3.setAnswers(List.of(answer1, answer2, answer3));
//        question3.setSection(Section.JAVA);
//        super.saveQuestion(question3);
//    }

}
