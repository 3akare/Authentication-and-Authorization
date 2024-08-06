package com.bakare.authentication_services.user.controller;

import com.bakare.authentication_services.user.dto.*;
import com.bakare.authentication_services.user.entity.User;
import com.bakare.authentication_services.user.service.AuthenticationService;
import com.bakare.authentication_services.user.service.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterDTO registerUserDto) {
        return ResponseEntity.ok(authenticationService.signup(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/github")
    public ResponseEntity<AuthResponseDTO> githubLogin(@RequestBody AuthRequestDTO authRequestDTO) {
        authRequestDTO.setClient_id(clientId);
        authRequestDTO.setClient_secret(clientSecret);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(authRequestDTO);
            var accessTokenResponse = getAccessToken(requestBody);
            assert accessTokenResponse != null;
            var accessUserApiResponse = accessUserApi((String) accessTokenResponse.get("access_token"));

            RegisterDTO registerDTO = new RegisterDTO();
            registerDTO.setEmail((String) accessUserApiResponse.get("email"));
            registerDTO.setName((String) accessUserApiResponse.get("name"));
            registerDTO.setPassword("hahah123$ajaj");

            return ResponseEntity.ok(authenticationService.signup(registerDTO));
        }
        catch (IOException | InterruptedException error){
            log.info("Http Error: {}", error.getLocalizedMessage());
            return null;
        }
    }

    private Map<String, Object> getAccessToken(String requestBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com/login/oauth/access_token"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});
            } else {
                log.error("Failed to get access token. Status code: {}", response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error while retrieving access token: {}", e.getMessage());
            return null;
        }
    }

    private Map<String, Object> accessUserApi(String accessToken) throws IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/user"))
            .header("Authorization","Bearer "+ accessToken)
            .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>() {});
    }

}
