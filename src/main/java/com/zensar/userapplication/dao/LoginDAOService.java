package com.zensar.userapplication.dao;

import com.zensar.userapplication.beans.User;

public interface LoginDAOService {
    boolean authenticateUser(User user);
}