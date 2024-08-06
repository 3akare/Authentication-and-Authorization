package com.bakare.authentication_services.user.service;

import com.bakare.authentication_services.user.dto.AuthResponseDTO;
import com.bakare.authentication_services.user.dto.LoginDTO;
import com.bakare.authentication_services.user.dto.RegisterDTO;
import com.bakare.authentication_services.user.entity.User;
import com.bakare.authentication_services.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO signup(RegisterDTO input) {
        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        userRepository.save(user);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setUsername(input.getName());
        authResponseDTO.setToken(jwtService.generateToken(user));
        authResponseDTO.setEmail(input.getEmail());

        return authResponseDTO;
    }

    public User authenticate(LoginDTO input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        return userRepository.findByEmail(input.getEmail())
            .orElseThrow();
    }
}