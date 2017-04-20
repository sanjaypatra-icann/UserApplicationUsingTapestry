package com.zensar.userapplication.services;

public interface ChangePasswordService {
    boolean changePassword(String username, String password, String newPassword);
}