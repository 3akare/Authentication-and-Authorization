package com.bakare.authentication_services.user.service;

import com.bakare.authentication_services.user.entity.User;
import com.bakare.authentication_services.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.bakare.authentication_services.user.dto.enums.Role.ADMIN;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler{

    private final UserRepository userRepository;

//    private final user;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        var user = userRepository.findByEmail(oAuth2User.getAttribute("email")).orElseThrow();

        user.setEmail(oAuth2User.getAttribute("email"));
        user.setRole(ADMIN);
        user.setUsername(oAuth2User.getAttribute("username"));
    }
}
