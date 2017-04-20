package com.zensar.userapplication.beans;

public class User {
    private int userID, countryCode;
    private String username, firstname, middlename, lastname, email, password;
    private long contactNo;
    public User() {}

    public User(int userID, int countryCode, long contactNo, String username, String firstname, String middlename, String lastname, String email, String password) {
        this.userID = userID;
        this.countryCode = countryCode;
        this.contactNo = contactNo;
        this.username = username;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    public User(int countryCode, long contactNo, String username, String firstname, String middlename, String lastname, String email, String password) {
        this.countryCode = countryCode;
        this.contactNo = contactNo;
        this.username = username;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(String username, String firstname, String middlename, String lastname, String password) {
        this.username = username;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.password = password;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getMiddlename() {
        return middlename;
    }
    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public int getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }
    public long getContactNo() {
        return contactNo;
    }
    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", countryCode=" + countryCode +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contactNo=" + contactNo +
                '}';
    }
}