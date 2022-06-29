package com.ipstresser.app.services;

import com.ipstresser.app.helpers.UserConfirmationCode;
import com.ipstresser.app.services.interfaces.ConfirmationService;
import com.ipstresser.app.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final EmailService emailService;
    private UserConfirmationCode userConfirmationCode;

    @Autowired
    public ConfirmationServiceImpl(EmailService emailService, UserConfirmationCode userConfirmationCode) {
        this.emailService = emailService;
        this.userConfirmationCode = userConfirmationCode;
    }

    @Override
    public String sendConfirmationEmail(String to) {
        String uuid = UUID.randomUUID().toString();
        this.emailService.sendEmail(to, "Confirm your account in IPStresser",
                String.format("Please confirm your account to get full access to our features.." +
                        "Code -> %s", uuid));

        this.userConfirmationCode = userConfirmationCode.setCode(uuid);

        return uuid;
    }

    @Override
    public boolean confirmConfirmationCode(String code) {
        return code.equals(this.userConfirmationCode.getCode());
    }
}
