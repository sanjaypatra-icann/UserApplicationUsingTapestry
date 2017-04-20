package com.zensar.userapplication.dao;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.connectionprovider.ConnectionProvider;
import com.zensar.userapplication.utils.EncryptPassword;
import java.sql.*;

public class RegisterDAOServiceImpl implements RegisterDAOService {
    private Connection connection = null;
    public RegisterDAOServiceImpl() {
        try {
            connection = ConnectionProvider.getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException!!" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
    }
    public boolean registerUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registrations(username, firstname, middlename, lastname, countryCode, contactNo, email, password, accountCreationDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getMiddlename());
            preparedStatement.setString(4, user.getLastname());
            preparedStatement.setInt(5, user.getCountryCode());
            preparedStatement.setLong(6, user.getContactNo());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.setString(8, EncryptPassword.getEncryptedPassword(user.getPassword()));
            preparedStatement.setDate(9, new Date(System.currentTimeMillis()));
            if (preparedStatement.executeUpdate() > 0)  return true;
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return false;
    }
}