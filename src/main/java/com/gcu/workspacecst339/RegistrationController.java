package com.gcu.workspacecst339;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.gcu.workspacecst339.model.User;
import com.gcu.workspacecst339.service.UserService;

import jakarta.servlet.http.HttpSession;

// Controller for handling user registration
@Controller
public class RegistrationController {
    // Service used to manage users
    private final UserService userService;

    // Constructor that injects the user service
    public RegistrationController(UserService userService){ this.userService=userService; }

    // Show the registration page with an empty user model
    @GetMapping("/register")
    public String show(Model model){
        model.addAttribute("pageTitle", "Register - CLC App");
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle the registration form submission
    @PostMapping("/register")
    public String submit(@ModelAttribute("user") User user, Model model, HttpSession session){
        // Validate basic fields and collect errors
        List<String> errors = validate(user);

        // Add error if the username is already used
        if(userService.usernameTaken(user.getUsername())) errors.add("Username already exists");

        // If there are errors then re render the form with messages
        if(!errors.isEmpty()){
            model.addAttribute("pageTitle", "Register - CLC App");
            model.addAttribute("errors", errors);
            return "register";
        }

        // Save the new user and log them in
        userService.register(user);
        session.setAttribute("user", user);

        // Go to the products page after success
        return "redirect:/products";
    }

    // Perform simple server side validation
    private List<String> validate(User u){
        // Start a new list of error messages
        List<String> e = new ArrayList<>();

        // First name is required
        if(u.getFirstName()==null || u.getFirstName().isBlank()) e.add("First name is required");

        // Last name is required
        if(u.getLastName()==null  || u.getLastName().isBlank())  e.add("Last name is required");

        // Email must contain the at symbol
        if(u.getEmail()==null     || !u.getEmail().contains("@"))e.add("Valid email is required");

        // Username is required
        if(u.getUsername()==null  || u.getUsername().isBlank())  e.add("Username is required");

        // Password must meet a minimum length
        if(u.getPassword()==null  || u.getPassword().length()<4) e.add("Password must be at least 4 characters");

        // Return the list of validation errors
        return e;
    }
}