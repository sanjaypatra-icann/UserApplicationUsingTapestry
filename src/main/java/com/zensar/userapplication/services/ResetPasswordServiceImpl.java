package com.zensar.userapplication.services;

import com.zensar.userapplication.dao.RegisterDAOService;
import com.zensar.userapplication.dao.RegisterDAOServiceImpl;
import com.zensar.userapplication.dao.ResetPasswordDAOService;
import com.zensar.userapplication.dao.ResetPasswordDAOServiceImpl;

public class ResetPasswordServiceImpl implements ResetPasswordService {
    ResetPasswordDAOService daoService = null;
    public ResetPasswordServiceImpl() {
        daoService = new ResetPasswordDAOServiceImpl();
    }
    public boolean resetPassword(String username, String newPassword) {
        return daoService.resetPassword(username, newPassword);
    }
}