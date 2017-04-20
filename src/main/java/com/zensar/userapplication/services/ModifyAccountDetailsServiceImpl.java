package com.zensar.userapplication.services;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.dao.ModifyAccountDetailsDAOService;
import com.zensar.userapplication.dao.ModifyAccountDetailsDAOServiceImpl;

public class ModifyAccountDetailsServiceImpl implements ModifyAccountDetailsService {
    ModifyAccountDetailsDAOService daoService = null;

    public ModifyAccountDetailsServiceImpl() {
        daoService = new ModifyAccountDetailsDAOServiceImpl();
    }

    public boolean modifyAccountDetails(int countryCode, long contactNo, String username, String firstname, String middlename, String lastname, String email) {
        return daoService.modifyAccountDetails(new User(countryCode, contactNo, username, firstname, middlename, lastname, email, ""));
    }
}