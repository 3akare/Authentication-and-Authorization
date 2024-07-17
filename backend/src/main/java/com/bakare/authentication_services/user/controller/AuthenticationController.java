package com.bakare.authentication_services.user.controller;

import com.bakare.authentication_services.user.dto.AuthenticationResponse;
import com.bakare.authentication_services.user.security.AuthRequest;
import com.bakare.authentication_services.user.security.RegisterRequest;
import com.bakare.authentication_services.user.service.AuthenticationService;

import java.security.Principal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/token")
    public String getToken(@RegisteredOAuth2AuthorizedClient("gitlab") OAuth2AuthorizedClient authorizedClient) {
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        System.out.println(accessToken.getTokenValue());
        // Store or use the access token as needed
        return "good";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse<?>> register(@RequestBody RegisterRequest request){
       return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse<?>> register(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.login(request));

    }
}
