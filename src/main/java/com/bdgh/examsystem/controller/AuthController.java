package com.bdgh.examsystem.controller;

import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdgh.examsystem.dto.Auth.AuthenticationRequest;
import com.bdgh.examsystem.dto.Auth.RegisterRequest;
import com.bdgh.examsystem.dto.Token.Token;
import com.bdgh.examsystem.entity.ResponseObject;
import com.bdgh.examsystem.service.AuthenticationService;
import com.bdgh.examsystem.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody @Valid RegisterRequest request) {

        Token token = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng ký thành công!", token));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseObject> register(@RequestBody @Valid AuthenticationRequest request) {

        Token token = authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng nhập thành công!", token));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestBody Map<String, String> emailMap)
            throws MessagingException {
        String email = emailMap.get("email");
        userService.forgotPassword(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Gửi email đặt lại mật khẩu thành công", ""));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseObject> resetPassword(@RequestBody Map<String, String> resetInfo) {
        userService.resetPassword(resetInfo);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đặt lại mật khẩu thành công", ""));
    }

    @PostMapping("/logout")
    ResponseEntity<ResponseObject> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody Map<String, String> body) {
        authenticationService.logout(authorizationHeader, body.get("refreshToken"));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng xuất thành công", null));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseObject> refreshAuthenticationToken(@RequestBody Map<String, String> body) {
        Token token = authenticationService.refreshToken(body.get("refreshToken"));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Làm mới token thành công!", token));
    }
}
