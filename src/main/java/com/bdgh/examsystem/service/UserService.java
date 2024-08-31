package com.bdgh.examsystem.service;

import java.util.Map;

import jakarta.mail.MessagingException;

import com.bdgh.examsystem.entity.User;

public interface UserService {
    User findUserByEmail(String email);

    void resetPassword(Map<String, String> resetInfo);

    void forgotPassword(String email) throws MessagingException;
}
