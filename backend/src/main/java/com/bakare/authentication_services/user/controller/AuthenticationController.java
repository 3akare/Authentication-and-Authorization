package com.bakare.authentication_services.user.controller;

import com.bakare.authentication_services.user.dto.AuthenticationResponse;
import com.bakare.authentication_services.user.security.AuthRequest;
import com.bakare.authentication_services.user.security.RegisterRequest;
import com.bakare.authentication_services.user.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse<?>> register(@RequestBody RegisterRequest request){
       return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse<?>> register(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.login(request));

    }
}
