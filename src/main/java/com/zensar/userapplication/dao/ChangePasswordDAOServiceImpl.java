package com.zensar.userapplication.dao;

import com.zensar.userapplication.connectionprovider.ConnectionProvider;
import com.zensar.userapplication.utils.EncryptPassword;

import java.sql.*;

public class ChangePasswordDAOServiceImpl implements ChangePasswordDAOService {
    private Connection connection = null;
    public ChangePasswordDAOServiceImpl() {
        try {
            connection = ConnectionProvider.getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException!!" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
    }
    public boolean changePassword(String username, String password, String newPassword) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE registrations SET password = ? WHERE username = ? AND password = ?;");
            preparedStatement.setString(1, EncryptPassword.getEncryptedPassword(newPassword));
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, EncryptPassword.getEncryptedPassword(password));
            if (preparedStatement.executeUpdate() > 0)   {
                preparedStatement = connection.prepareStatement("INSERT INTO passwords(username, passwordChangedTime, password) VALUES(?, ?, ?);");
                preparedStatement.setString(1, username);
                preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                preparedStatement.setString(3, EncryptPassword.getEncryptedPassword(password));
                if (preparedStatement.executeUpdate() > 0)  return true;
                else {
                    preparedStatement = connection.prepareStatement("UPDATE registrations SET password = ? WHERE username = ? AND password = ?;");
                    preparedStatement.setString(1, password);
                    preparedStatement.setString(2, username);
                    preparedStatement.setString(3, EncryptPassword.getEncryptedPassword(newPassword));
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return false;
    }
}