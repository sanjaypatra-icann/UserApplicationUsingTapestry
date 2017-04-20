package com.zensar.userapplication.services;

import com.zensar.userapplication.dao.ChangePasswordDAOService;
import com.zensar.userapplication.dao.ChangePasswordDAOServiceImpl;

public class ChangePasswordServiceImpl implements ChangePasswordService {
    ChangePasswordDAOService daoService = null;
    public ChangePasswordServiceImpl() {
        daoService = new ChangePasswordDAOServiceImpl();
    }
    public boolean changePassword(String username, String password, String newPassword) {
        return daoService.changePassword(username, password, newPassword);
    }
}