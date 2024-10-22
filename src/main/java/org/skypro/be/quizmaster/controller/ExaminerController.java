package org.skypro.be.quizmaster.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.skypro.be.quizmaster.model.ExamQuestion;
import org.skypro.be.quizmaster.model.Question;
import org.skypro.be.quizmaster.model.User;
import org.skypro.be.quizmaster.model.dto.ExamSettingDto;
import org.skypro.be.quizmaster.service.ExaminerService;
import org.skypro.be.quizmaster.service.ResultService;
import org.skypro.be.quizmaster.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/exam")
public class ExaminerController {

    private final ExaminerService examinerService;
    private final ResultService resultService;
    private final UserService userService;

    public ExaminerController(ExaminerService examinerService, ResultService resultService, UserService userService) {
        this.examinerService = examinerService;
        this.resultService = resultService;
        this.userService = userService;
    }

    @GetMapping("")
    public String viewSetup(Model model) {
        model.addAttribute("title", "Настройка параметров тестирования");
        model.addAttribute("examSettings", examinerService.getDefaultSettings());
        return "exam/setup";
    }

    @PostMapping("/start")
    public String startExam(@Valid @ModelAttribute("examSettings") ExamSettingDto examSettings, BindingResult result,
                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Необходимо выбрать хотя бы один раздел и хотя бы один тип вопроса");
            return "redirect:/exam";
        }

        model.addAttribute("title", "Начало тестирования");
        List<Question> examQuestions = examinerService.getQuestions(examSettings);
        session.setAttribute("examQuestions", examQuestions);
        session.setAttribute("examDescription", examinerService.getExamDescription(examSettings));
        model.addAttribute("examQuestions", examQuestions);

        return "exam/start";
    }

    @PostMapping("/result")
    public String startExam(@AuthenticationPrincipal User user, @RequestParam LinkedMultiValueMap<String, String> answers,
                            Model model, HttpSession session) {
        @SuppressWarnings("unchecked") List<Question> examQuestions = (List<Question>) session.getAttribute("examQuestions");
        String description = (String) session.getAttribute("examDescription");
        answers.remove("_csrf");
        List<ExamQuestion> examResults = resultService.getResult(examQuestions, answers);
        resultService.saveResult(user.getId(), examResults, description);
        model.addAttribute("examResults", examResults);
        model.addAttribute("totalQuestions", examResults.size());
        model.addAttribute("correctAnswersCount", examResults.stream().filter(ExamQuestion::getCorrect).count());

        return "exam/result";
    }

    @GetMapping("/{userId}")
    public String viewUserResults(@PathVariable Long userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("title", "Результаты тестирования:" + user.getUsername());
        model.addAttribute("userId", userId);
        model.addAttribute("examResults", resultService.getUserResults(userId));
        return "exam/user-result";
    }

}
