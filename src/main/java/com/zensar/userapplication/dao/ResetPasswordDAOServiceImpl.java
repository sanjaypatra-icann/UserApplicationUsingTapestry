package com.zensar.userapplication.dao;

import com.zensar.userapplication.connectionprovider.ConnectionProvider;
import com.zensar.userapplication.utils.EncryptPassword;
import com.zensar.userapplication.utils.GetUserDetails;

import java.sql.*;

public class ResetPasswordDAOServiceImpl implements ResetPasswordDAOService {
    private Connection connection = null;
    public ResetPasswordDAOServiceImpl() {
        try {
            connection = ConnectionProvider.getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException!!" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
    }
    public boolean resetPassword(String username, String newPassword) {
        String password = GetUserDetails.getUserDetails(username).getPassword();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE registrations SET password = ? WHERE username = ? AND password = ?;");
            preparedStatement.setString(1, EncryptPassword.getEncryptedPassword(newPassword));
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            if (preparedStatement.executeUpdate() > 0)   {
                preparedStatement = connection.prepareStatement("INSERT INTO passwords(username, passwordChangedDate, password) VALUES(?, ?, ?);");
                preparedStatement.setString(1, username);
                preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
                preparedStatement.setString(3, password);
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