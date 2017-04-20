package com.zensar.userapplication.serviceprovider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.zensar.userapplication.dao.RegisterDAOService;
import com.zensar.userapplication.exceptions.ServicesDownException;
import com.zensar.userapplication.services.RegisterService;

public class ServiceProvider {
    private static Properties properties;
    static {
        String filePath = "D:/IdeaProjects/UserApplicationUsingTapestry/src/main/resources/userapp.properties";
        if (System.getenv("PROPERTIES_FILE") != null)   filePath = System.getenv("PROPERTIES_FILE");
        try {
            properties = new Properties();
            properties.load(new FileInputStream(new File(filePath)));
        } catch (IOException e) {
            System.err.println("Some error has occurred!" + e.getMessage());
        }
    }
    public static RegisterService getRegisterService() throws ServicesDownException {
        try {
            return (RegisterService) Class.forName(properties.getProperty("registerService")).newInstance();
        } catch (InstantiationException e) {
            throw new ServicesDownException("Services are down. Please try again later!", e);
        } catch (IllegalAccessException e) {
            throw new ServicesDownException("Services are down. Please try again later!", e);
        } catch (ClassNotFoundException e) {
            throw new ServicesDownException("Services are down. Please try again later!", e);
        }
    }

    public static RegisterDAOService getRegisterDAOService() throws ServicesDownException {
        try {
            return (RegisterDAOService) Class.forName(properties.getProperty("registerDAOService")).newInstance();
        } catch (InstantiationException e) {
            throw new ServicesDownException("Services are down. Please try again later!", e);
        } catch (IllegalAccessException e) {
            throw new ServicesDownException("Services are down. Please try again later!", e);
        } catch (ClassNotFoundException e) {
            throw new ServicesDownException("Services are down. Please try again later!", e);
        }
    }

    public static int getNoOfLastPasswords() {
        return Integer.parseInt(properties.getProperty("noOfLastPasswords"));
    }
}