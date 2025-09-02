package com.gcu.topic1.controllers;

import com.gcu.topic1.dto.LoginForm;
import com.gcu.topic1.dto.RegistrationForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {

    @GetMapping({"/", "/index"})
    public String index(HttpServletRequest req, Model model) {
        model.addAttribute("serverPort", req.getServerPort());
        return "index";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        if (!model.containsAttribute("loginForm")) {
            model.addAttribute("loginForm", new LoginForm());
        }
        model.addAttribute("title", "Login");
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("loginForm") LoginForm form,
                          BindingResult result,
                          RedirectAttributes ra) {
        if (result.hasErrors()) return "login";
        ra.addFlashAttribute("message", "Welcome back, " + form.getUsername() + "!");
        return "redirect:/products";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        if (!model.containsAttribute("registrationForm")) {
            model.addAttribute("registrationForm", new RegistrationForm());
        }
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
                             BindingResult result,
                             RedirectAttributes ra) {
        if (result.hasErrors()) return "register";
        ra.addFlashAttribute("message", "Account created! Please sign in.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}