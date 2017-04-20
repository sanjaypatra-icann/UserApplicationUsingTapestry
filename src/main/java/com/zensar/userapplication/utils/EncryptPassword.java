package com.zensar.userapplication.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {
    public static String getEncryptedPassword(String password) {
        String encryptedPassword = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = messageDigest.digest(password.getBytes());
            BigInteger number = new BigInteger(1, passwordBytes);
            encryptedPassword = number.toString(16);
            while (encryptedPassword.length() < 32) {
                encryptedPassword = "0" + encryptedPassword;
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("NoSuchAlgorithmException!!" + e.getMessage());
        }
        return encryptedPassword;
    }
}