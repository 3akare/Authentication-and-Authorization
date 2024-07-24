package com.bakare.authentication_services.user.controller;

import com.bakare.authentication_services.user.dto.AuthRequestDTO;
import com.bakare.authentication_services.user.dto.AuthResponseDTO;
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
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @PostMapping("/github")
    public ResponseEntity<AuthResponseDTO> githubLogin(@RequestBody AuthRequestDTO authRequestDTO) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();

        authRequestDTO.setClient_id(clientId);
        authRequestDTO.setClient_secret(clientSecret);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(authRequestDTO);

//            log.info("{} {}", requestBody, authRequestDTO);
            var accessTokenResponse = getAccessToken(requestBody);
            assert accessTokenResponse != null;

            var accessUserApiResponse = accessUserApi((String) accessTokenResponse.get("access_token"));

            authResponseDTO.setEmail((String) accessUserApiResponse.get("email"));
            authResponseDTO.setUser((String) accessUserApiResponse.get("name"));
            authResponseDTO.setToken((String) accessTokenResponse.get("access_token"));
            return ResponseEntity.ok(authResponseDTO);
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
