package com.gcu.workspacecst339;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controller for rendering the home page
@Controller
public class HomeController {

    // Handles a get request for the root path
    @GetMapping("/")
    public String home(Model model) {
        // Adds the page title for the view
        model.addAttribute("pageTitle", "Home - CLC App");
        // Returns the home view name
        return "home";
    }
}