package com.bakare.authentication_services.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubAuthRequest {
    private String code;
    private String client_id;
    private String client_secret;
}
