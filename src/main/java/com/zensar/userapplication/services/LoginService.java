package com.zensar.userapplication.services;

import com.zensar.userapplication.beans.User;

public interface LoginService {
    boolean authenticate(String username, String password);
}