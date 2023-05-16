package com.example.toolinventorysystem.services;

public interface EmailService {
    void sendMail(String subject, String body, String toMail);
}
