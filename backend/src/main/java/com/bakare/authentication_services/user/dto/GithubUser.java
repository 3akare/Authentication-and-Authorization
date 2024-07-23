package com.bakare.authentication_services.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class GithubUser {
    private String login;
    private String name;
    private String email;
}
