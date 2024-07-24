package com.bakare.authentication_services.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO{
    private String client_id;
    private String client_secret;
    private String code;
}
