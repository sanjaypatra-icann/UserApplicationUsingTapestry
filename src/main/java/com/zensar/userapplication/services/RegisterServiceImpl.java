package com.zensar.userapplication.services;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.dao.RegisterDAOService;
import com.zensar.userapplication.dao.RegisterDAOServiceImpl;

public class RegisterServiceImpl implements RegisterService {
    RegisterDAOService daoService = null;
    public RegisterServiceImpl() {
        daoService = new RegisterDAOServiceImpl();
    }
    public boolean register(int countryCode, long contactNo, String username, String firstname, String middlename, String lastname, String email, String password) {
        return daoService.registerUser(new User(countryCode, contactNo, username, firstname, middlename, lastname, email, password));
    }
}
