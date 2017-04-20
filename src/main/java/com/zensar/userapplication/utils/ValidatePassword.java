package com.zensar.userapplication.utils;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.connectionprovider.ConnectionProvider;
import com.zensar.userapplication.serviceprovider.ServiceProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidatePassword {
    private static Connection connection = null;
    static {
        try {
            connection = ConnectionProvider.getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException!!" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
    }
    public static boolean validatePassword(User user) {
        String password = user.getPassword().toLowerCase();
        if (password.matches("(?i:.*" + user.getUsername() + ".*)") || password.matches("(?i:.*" + user.getFirstname() + ".*)") || password.matches("(?i:.*" + user.getLastname() + ".*)"))
            return false;
        else if (user.getMiddlename() != null && !user.getMiddlename().trim().equals("") && user.getMiddlename().length() != 0)
            if (password.matches("(?i:.*" + user.getMiddlename() + ".*)"))
                return false;
        return true;
    }
    public static boolean checkPasswordInRecentPasswords(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM (SELECT * FROM passwords WHERE username = ? ORDER BY passwordChangedTime DESC LIMIT 0," + ServiceProvider.getNoOfLastPasswords() + ") AS passwords WHERE password = ?;");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())  return false;
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return true;
    }
}