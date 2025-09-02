package com.gcu.workspacecst339;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest controller that provides a simple health check
@RestController
public class PingController {

    // Responds to a get request at the ping path
    @GetMapping("/_ping")
    public String ping() {
        // Returns a plain ok message
        return "ok";
    }
}