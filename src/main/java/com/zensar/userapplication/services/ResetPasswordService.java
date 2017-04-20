package com.zensar.userapplication.services;

public interface ResetPasswordService {
    boolean resetPassword(String username, String newPassword);
}