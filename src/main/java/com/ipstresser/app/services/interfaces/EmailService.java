package com.ipstresser.app.services.interfaces;

public interface EmailService {

    void sendEmail(String to, String title, String content);
}
