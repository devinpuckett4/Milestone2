package com.gcu.workspacecst339;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.gcu.workspacecst339.model.LoginForm;
import com.gcu.workspacecst339.model.User;
import com.gcu.workspacecst339.service.UserService;

import jakarta.servlet.http.HttpSession;

// Controller for handling login and logout
@Controller
public class LoginController {
    // Service for user lookup and authentication
    private final UserService userService;

    // Inject the user service
    public LoginController(UserService userService){ this.userService=userService; }

    // Show the login page with an empty form
    @GetMapping("/login")
    public String show(Model model){
        model.addAttribute("pageTitle", "Login - CLC App");
        model.addAttribute("login", new LoginForm());
        return "login";
    }

    // Process the submitted login form
    @PostMapping("/login")
    public String submit(@ModelAttribute("login") LoginForm form, Model model, HttpSession session){
        // Collect simple validation errors
        List<String> errors = new ArrayList<>();

        // Ensure username is provided
        if(form.getUsername()==null || form.getUsername().isBlank()) errors.add("Username is required");

        // Ensure password is provided
        if(form.getPassword()==null || form.getPassword().isBlank()) errors.add("Password is required");

        // If there are errors then re render the login page with messages
        if(!errors.isEmpty()){
            model.addAttribute("pageTitle", "Login - CLC App");
            model.addAttribute("errors", errors);
            return "login";
        }

        // Check credentials with the service
        User u = userService.authenticate(form.getUsername(), form.getPassword());

        // If credentials are invalid then show the login page again
        if(u==null){
            model.addAttribute("pageTitle", "Login - CLC App");
            model.addAttribute("errors", List.of("Invalid username or password"));
            return "login";
        }

        // Store the user in the session and go to products
        session.setAttribute("user", u);
        return "redirect:/products";
    }

    // Clear the session and return to the home page
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}