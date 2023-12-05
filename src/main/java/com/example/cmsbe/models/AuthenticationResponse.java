package com.example.cmsbe.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String message;
    private User user;
}
