package com.zensar.userapplication.utils;

import com.zensar.userapplication.connectionprovider.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckEmailExists {
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
    public static boolean checkIfEmailExists(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT email FROM registrations WHERE email = ?;");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())  return true;
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return false;
    }
    public static boolean checkIfEmailIsUsedByOtherUser(String username, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT email FROM registrations WHERE username != ? AND email = ?;");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())  return true;
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return false;
    }
}