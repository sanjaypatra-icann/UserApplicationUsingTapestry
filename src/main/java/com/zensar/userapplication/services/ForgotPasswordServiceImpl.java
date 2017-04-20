package com.zensar.userapplication.services;

import com.zensar.userapplication.dao.ForgotPasswordDAOService;
import com.zensar.userapplication.dao.ForgotPasswordDAOServiceImpl;

public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    ForgotPasswordDAOService daoService = null;
    public ForgotPasswordServiceImpl() {
        daoService = new ForgotPasswordDAOServiceImpl();
    }
    public boolean getPasswordResetLink(String username, String email) {
        return daoService.getPasswordResetLink(username, email);
    }
}