package org.skypro.be.quizmaster.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.skypro.be.quizmaster.model.User;
import org.skypro.be.quizmaster.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class); //TODO Заменить на аннотацию и добавить логирование

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("title", "Регистрация нового пользователя");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                               @RequestParam String repeatPassword, HttpServletRequest request,
                               Model model) {
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "exists",
                    "Пользователь с таким именем уже существует");
        }
        if (!user.getPassword().equals(repeatPassword)) {
            bindingResult.rejectValue("password", "mismatch",
                    "Введенные пароли не совпадают");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.registerUser(user);
        userService.authenticate(user.getUsername(), repeatPassword, request);

        return "redirect:/exam";
    }

}
