package org.skypro.be.quizmaster.controller;

import org.skypro.be.quizmaster.model.ExamSetting;
import org.skypro.be.quizmaster.model.ExamSettingDto;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.service.ExaminerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/exam")
public class ExaminerController {

    @Autowired
    private ExaminerService examinerService;

    @GetMapping("")
    public String viewSetup (Model model) {
        model.addAttribute("title", "Настройка параметров тестирования");
        model.addAttribute("examSettings", examinerService.getDefaultSettings());
        return "exam/setup";
    }

    @PostMapping("/setup")
    public String startExam (Model model, ExamSettingDto examSettings,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("title", "Настройка параметров тестирования");
            redirectAttributes.addFlashAttribute("examSettings", examSettings);
            return "redirect: /exam";
        }

        model.addAttribute("title", "Начало тестирования");
        List<Question> examQuestions=examinerService.getQuestions(examSettings);
        return "redirect:/exam";
//        examinerService.setDefaultSettings(examSettings);
//        return "redirect:/exam/start";
    }

}
