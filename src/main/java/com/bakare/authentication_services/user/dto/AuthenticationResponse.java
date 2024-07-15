package com.bakare.authentication_services.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse<T> {
    private Integer statusCode;
    private String authToken;
    private T data;
}
