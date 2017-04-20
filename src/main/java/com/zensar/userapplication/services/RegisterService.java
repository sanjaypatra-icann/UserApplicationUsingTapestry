package com.zensar.userapplication.services;

public interface RegisterService {
    boolean register(int countryCode, long contactNo, String username, String firstname, String middlename, String lastname, String email, String password);
}