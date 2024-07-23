package com.bakare.authentication_services.user.controller;

import com.bakare.authentication_services.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class userController {

    // Get authenticated user info
    @GetMapping
    public ResponseEntity<?> getUser(Authentication authentication) {
        String username = authentication.getName();
        // Fetch user details from your database or create a user object
        User user = new User(username);
        return ResponseEntity.ok(user);
    }
}