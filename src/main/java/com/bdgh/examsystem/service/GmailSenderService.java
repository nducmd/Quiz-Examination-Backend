package com.bdgh.examsystem.service;

import jakarta.mail.MessagingException;

import com.bdgh.examsystem.entity.User;

public interface GmailSenderService {
    void sendEmailResetPassword(User user) throws MessagingException;
}
