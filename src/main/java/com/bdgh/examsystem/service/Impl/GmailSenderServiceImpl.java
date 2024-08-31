package com.bdgh.examsystem.service.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bdgh.examsystem.entity.User;
import com.bdgh.examsystem.service.GmailSenderService;

@Service
public class GmailSenderServiceImpl implements GmailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmailResetPassword(User user) throws MessagingException {
        Context context = new Context();
        String link = "http://localhost:3000/resetPassword?token=" + user.getResetPasswordToken();
        context.setVariable("link", link);

        String process = templateEngine.process("resetPasswordEmail", context);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setSubject("Thông báo đặt lại mật khẩu từ Hệ thống thi trắc nghiệm PTIT");
        helper.setText(process, true);
        helper.setTo(user.getEmail());
        emailSender.send(mimeMessage);
    }
}
