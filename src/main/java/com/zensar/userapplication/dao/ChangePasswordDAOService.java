package com.zensar.userapplication.dao;

public interface ChangePasswordDAOService {
    boolean changePassword(String username, String password, String newPassword);
}