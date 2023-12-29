package com.example.cmsbe.services.interfaces;

import com.example.cmsbe.models.AuthenticationRequest;
import com.example.cmsbe.models.AuthenticationResponse;
import com.example.cmsbe.models.RegisterRequest;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
//    String encodePassword(String password);
}
