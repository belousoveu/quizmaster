package org.skypro.be.quizmaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) Boolean error, Model model) {
        if (error != null && error) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        model.addAttribute("title", "Авторизация пользователя");
        return "login";
    }
}
