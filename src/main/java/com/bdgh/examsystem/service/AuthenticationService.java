package com.bdgh.examsystem.service;

import com.bdgh.examsystem.dto.Auth.AuthenticationRequest;
import com.bdgh.examsystem.dto.Auth.RegisterRequest;
import com.bdgh.examsystem.dto.Token.Token;

public interface AuthenticationService {
    Token register(RegisterRequest request);

    Token authenticate(AuthenticationRequest request);

    void logout(String authorizationHeader, String refreshToken);

    Token refreshToken(String refreshToken);
}
