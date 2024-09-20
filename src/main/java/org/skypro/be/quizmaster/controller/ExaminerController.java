package org.skypro.be.quizmaster.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.User;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.skypro.be.quizmaster.service.ExaminerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/start")
    public String startExam (@Valid @ModelAttribute("examSettings") ExamSettingDto examSettings, BindingResult result,
                             HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("title", "Настройка параметров тестирования");
            redirectAttributes.addFlashAttribute("examSettings", examSettings);
            return "redirect: /exam";
        }

        model.addAttribute("title", "Начало тестирования");
        List<Question> examQuestions=examinerService.getQuestions(examSettings);
        session.setAttribute("examQuestions", examQuestions);
        model.addAttribute("examQuestions", examQuestions);

        return "exam/start";
    }

    @PostMapping("/result")
    public String startExam(@AuthenticationPrincipal User user, @RequestParam LinkedMultiValueMap<String, String> answers,
                            Model model, HttpSession session) {
        List<Question> examQuestions = (List<Question>) session.getAttribute("examQuestions");
        answers.remove("_csrf");
        List<ExamQuestion> examResults= examinerService.getResult(examQuestions, answers);
        // TODO записать результат в БД
        model.addAttribute("examResults", examResults);
        model.addAttribute("totalQuestions", examResults.size());
        model.addAttribute("correctAnswersCount", examResults.stream().filter(ExamQuestion::getCorrect).count());

        return "exam/result";
    }

}
