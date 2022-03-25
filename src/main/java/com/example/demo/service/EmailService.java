package com.example.demo.service;

import javax.mail.MessagingException;

public interface EmailService {
    boolean sendMail(String to, String subject, String text);

    boolean sendMailwithFile(String to, String subject, String text, String filePath);

}
