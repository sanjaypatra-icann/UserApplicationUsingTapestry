package com.zensar.userapplication.services;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.dao.LoginDAOService;
import com.zensar.userapplication.dao.LoginDAOServiceImpl;

public class LoginServiceImpl implements LoginService {
    LoginDAOService daoService = null;
    public LoginServiceImpl() {
        daoService = new LoginDAOServiceImpl();
    }
    public boolean authenticate(String username, String password) {
        return daoService.authenticateUser(new User(username, password));
    }
}