package com.trade.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public void sendVerificationOTPEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Verify OTP to login";
        String bodyText = "Your verification code is " + otp;
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(bodyText);
        mimeMessageHelper.setTo(email);

        try {
            javaMailSender.send(mimeMessage);
        } catch (MailSendException e) {
            throw new MailSendException(e.getMessage());
        }

    }
}
