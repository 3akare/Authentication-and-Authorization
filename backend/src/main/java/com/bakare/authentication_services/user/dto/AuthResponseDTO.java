package com.bakare.authentication_services.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO {
    private String token;
    private String username;
    private String email;
}
