package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamSetting;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;

import java.util.List;

public interface ExaminerService {

    default ExamSetting getDefaultSettings() {
        return new ExamSetting();
    }

    List<Question> getQuestions(ExamSettingDto examSettings);

    String getExamDescription(ExamSettingDto examSettings);
}
