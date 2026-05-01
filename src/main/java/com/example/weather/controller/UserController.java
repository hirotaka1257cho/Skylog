package com.example.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.weather.form.UserRegisterForm;
import com.example.weather.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(UserRegisterForm registerForm, Model model) {
        model.addAttribute("registerForm", registerForm);
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerForm") @Validated UserRegisterForm registerForm,
            BindingResult result) {
        if (result.hasErrors()) {
            return "users/register";
        }
        if(!registerForm.getPassword().equals(registerForm.getConfirmPassword())){
            result.rejectValue("confirmPassword", "error", "パスワードが一致しません");
            return "users/register";
        }
        userService.register(registerForm);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }
}
