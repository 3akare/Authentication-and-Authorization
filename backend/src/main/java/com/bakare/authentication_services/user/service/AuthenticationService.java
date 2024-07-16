package com.bakare.authentication_services.user.service;

import com.bakare.authentication_services.user.dto.AuthenticationResponse;
import com.bakare.authentication_services.user.dto.enums.Role;
import com.bakare.authentication_services.user.entity.User;
import com.bakare.authentication_services.user.repository.UserRepository;
import com.bakare.authentication_services.user.security.AuthRequest;
import com.bakare.authentication_services.user.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse<?> register(RegisterRequest request){
        var user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().authToken(jwtToken).build();
    }

    public AuthenticationResponse<?> login(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().authToken(jwtToken).build();
    }
}

