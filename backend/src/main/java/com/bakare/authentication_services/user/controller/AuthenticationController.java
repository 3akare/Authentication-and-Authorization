package com.bakare.authentication_services.user.controller;

import com.bakare.authentication_services.user.dto.AuthResponse;
import com.bakare.authentication_services.user.dto.GithubAuthRequest;
import com.bakare.authentication_services.user.dto.GithubUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public void githubLogin(@RequestBody Map<String,String> body) throws IOException, InterruptedException {
        Map<String,String> jsonData = new HashMap<>();

        jsonData.put("client_id", "Ov23limIIiCD55RC0HXp");
        jsonData.put("client_secret", "63cf59ae87c298b5a5974ea408569f19357ad0da");
        jsonData.put("code", body.get("code"));

        log.info("{}", jsonData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com/login/oauth/access_token"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData.toString()))
                .build();

        HttpResponse<?> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("{}", response);
    }
}
