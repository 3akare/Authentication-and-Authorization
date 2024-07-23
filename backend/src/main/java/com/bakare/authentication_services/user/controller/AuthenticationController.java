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
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    @PostMapping("/github")
    public ResponseEntity<?> githubLogin(@RequestBody GithubAuthRequest request) {
        try {
            String accessToken = exchangeCodeForToken(request.getCode());
            log.info("Access token: {}", accessToken);

            GithubUser githubUser = fetchGithubUser(accessToken);
            log.info("GitHub user: {}", githubUser);

//            String email = fetchGithubUserEmail(accessToken);
//            log.info("GitHub user email: {}", email);

            String jwt = createJWT(githubUser, "bakaredavid007@gmail.com");
            log.info("JWT: {}", jwt);

            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            log.error("GitHub login failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("GitHub login failed");
        }
    }

    private String exchangeCodeForToken(String code) {
        String tokenUrl = "https://github.com/login/oauth/access_token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", code);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, entity, String.class);
        log.info("Token exchange response: {}", response.getBody());

        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Error exchanging code for token. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("Failed to exchange code for token");
        }

        return parseAccessToken(Objects.requireNonNull(response.getBody()));
    }

    private GithubUser fetchGithubUser(String accessToken) {
        String userInfoUrl = "https://api.github.com/user";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GithubUser> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, GithubUser.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Error fetching GitHub user. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("Failed to fetch GitHub user");
        }

        log.info("GitHub user response: {}", response.getBody());
        return response.getBody();
    }

    private String fetchGithubUserEmail(String accessToken) {
        String userEmailUrl = "https://api.github.com/user/emails";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String[]> response = restTemplate.exchange(userEmailUrl, HttpMethod.GET, entity, String[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Error fetching GitHub user email. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("Failed to fetch GitHub user email");
        }

        log.info("GitHub user email response: {}", response.getBody());
        String[] emails = response.getBody();
        for (String email : emails) {
            // Assume the first email is the primary email, you may need more logic here to handle multiple emails
            return email;
        }
        throw new RuntimeException("No email found for the GitHub user");
    }

    private String createJWT(GithubUser user, String email) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("name", user.getName())
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // 10 days
                .signWith(SignatureAlgorithm.HS512, "YOUR_SECRET".getBytes())
                .compact();
    }

    private String parseAccessToken(String response) {
        String[] pairs = response.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if ("access_token".equals(keyValue[0])) {
                log.info("Access Token from parse: {}", keyValue[1]);
                return keyValue[1];
            }
        }
        throw new RuntimeException("No access token found in the response");
    }
}
