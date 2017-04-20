package com.zensar.userapplication.dao;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.connectionprovider.ConnectionProvider;
import com.zensar.userapplication.utils.EncryptPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifyAccountDetailsDAOServiceImpl implements ModifyAccountDetailsDAOService {
    private Connection connection = null;
    public ModifyAccountDetailsDAOServiceImpl() {
        try {
            connection = ConnectionProvider.getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException!!" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
    }
    public boolean modifyAccountDetails(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE registrations SET firstname = ?, middlename = ?, lastname = ?, countryCode = ?, contactNo = ?, email = ? WHERE username = ?;");
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getMiddlename());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, String.valueOf(user.getCountryCode()));
            preparedStatement.setString(5, String.valueOf(user.getContactNo()));
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getUsername());
            if (preparedStatement.executeUpdate() > 0)   return true;
        } catch (SQLException e) {
            System.err.println("SQLException!!" + e.getMessage());
        }
        return false;
    }
}