package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.ExamSetting;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;

import java.util.List;
import java.util.Map;

public interface ExaminerService {

    default ExamSetting getDefaultSettings() {
        return new ExamSetting();
    }

    List<Question> getQuestions(ExamSettingDto examSettings);

    List<ExamQuestion> getResult(List<Question> questions, Map<String, List<String>> answers);

    String getExamDescription(ExamSettingDto examSettings);
}
