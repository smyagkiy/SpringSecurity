package ru.myagkiy.SpringSecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.myagkiy.SpringSecurity.model.Person;
import ru.myagkiy.SpringSecurity.services.RegistrationService;
import ru.myagkiy.SpringSecurity.util.PersonValidator;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person")Person person){
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "/auth/registration";
        registrationService.registration(person);
        return "redirect:/auth/login";
    }
}
