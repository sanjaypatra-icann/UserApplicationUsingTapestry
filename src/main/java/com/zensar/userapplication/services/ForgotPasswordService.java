package com.zensar.userapplication.services;

public interface ForgotPasswordService {
    boolean getPasswordResetLink(String username, String email);
}