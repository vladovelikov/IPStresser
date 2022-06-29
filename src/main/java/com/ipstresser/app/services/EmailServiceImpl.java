package com.ipstresser.app.services;

import com.ipstresser.app.services.interfaces.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String FROM_MAIL = "test@mail.bg";
    private JavaMailSender sender;

    @Override
    public void sendEmail(String to, String title, String content) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(FROM_MAIL);
        email.setTo(to);
        email.setSubject(title);
        email.setText(content);
        sender.send(email);
    }
}
