package org.skypro.be.quizmaster.service;

import org.skypro.be.quizmaster.model.ExamSetting;
import org.springframework.stereotype.Service;

@Service
public class ExaminerService {

    public ExamSetting getDefaultSettings() {
        return new ExamSetting();
    }
}
