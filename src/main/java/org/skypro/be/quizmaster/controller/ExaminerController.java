package org.skypro.be.quizmaster.controller;

import org.skypro.be.quizmaster.model.ExamSetting;
import org.skypro.be.quizmaster.service.ExaminerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exam")
public class ExaminerController {

    @Autowired
    private ExaminerService examinerService;

    @GetMapping("")
    public String viewSetup (Model model) {
        model.addAttribute("title", "Настройка параметров тестирования");
        model.addAttribute("examSetup", examinerService.getDefaultSettings());
        return "exam/setup";
    }

}
