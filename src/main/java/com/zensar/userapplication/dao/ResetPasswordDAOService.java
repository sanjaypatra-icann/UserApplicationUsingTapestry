package com.zensar.userapplication.dao;

public interface ResetPasswordDAOService {
    boolean resetPassword(String username, String newPassword);
}