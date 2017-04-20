package com.zensar.userapplication.dao;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.connectionprovider.ConnectionProvider;
import com.zensar.userapplication.utils.EncryptPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOServiceImpl implements LoginDAOService {
    private Connection connection = null;
    public LoginDAOServiceImpl() {
        try {
            connection = ConnectionProvider.getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException!!" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
    }
    public boolean authenticateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM registrations WHERE username = ? AND password = ?;");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, EncryptPassword.getEncryptedPassword(user.getPassword()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())    return true;
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return false;
    }
}