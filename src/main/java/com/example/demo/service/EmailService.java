package com.example.demo.service;

public interface EmailService {
    boolean sendMail(String to, String subject, String text);
}
