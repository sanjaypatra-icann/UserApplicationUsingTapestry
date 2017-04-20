package com.zensar.userapplication.dao;

public interface ForgotPasswordDAOService {
    boolean getPasswordResetLink(String username, String email);
}