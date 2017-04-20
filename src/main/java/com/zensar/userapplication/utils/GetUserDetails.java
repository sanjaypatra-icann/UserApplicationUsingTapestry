package com.zensar.userapplication.utils;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.connectionprovider.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserDetails {
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
    public static User getUserDetails(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM registrations WHERE username = ?;");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())    return new User(resultSet.getInt("userID"), resultSet.getInt("countryCode"), resultSet.getLong("contactNo"), resultSet.getString("username"), resultSet.getString("firstname"), resultSet.getString("middlename"), resultSet.getString("lastname"), resultSet.getString("email"), resultSet.getString("password"));
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return null;
    }
}