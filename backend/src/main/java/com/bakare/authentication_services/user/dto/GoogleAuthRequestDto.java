package com.bakare.authentication_services.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleAuthRequestDto extends AuthRequestDTO {
    private String redirect_uri;
    private String grant_type;
}
