package com.zensar.userapplication.services;

import com.zensar.userapplication.beans.User;

public interface ModifyAccountDetailsService {
    boolean modifyAccountDetails(int countryCode, long contactNo, String username, String firstname, String middlename, String lastname, String email);
}