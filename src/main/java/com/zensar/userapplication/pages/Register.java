package com.zensar.userapplication.pages;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.services.RegisterService;
import com.zensar.userapplication.utils.CheckEmailExists;
import com.zensar.userapplication.utils.CheckUsernameExists;
import com.zensar.userapplication.utils.ValidatePassword;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

    @Property
    @Validate("required, regexp=^[a-zA-Z0-9]+$")
    private String username;

    @Property
    @Validate("required, regexp=^[a-zA-Z]+$")
    private String firstname;

    @Property
    @Validate("regexp=^[a-zA-Z]+$")
    private String middlename;

    @Property
    @Validate("required, regexp=^[a-zA-Z]+$")
    private String lastname;

    @Property
    @Validate("required, minlength=1, maxlength=3, regexp=^[0-9]{1,3}$")
    private int countryCode;

    @Property
    @Validate("required, minlength=8, maxlength=10, regexp=^[0-9]{8,10}$")
    private long contactNo;

    @Property
    @Validate("required, email")
    private String email;

    @Property
    @Validate("required, minlength=8, maxlength=16, regexp=^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")
    private String password;

    @Property
    @Validate("required")
    private String confirmPassword;

    @InjectComponent("registrationForm")
    private Form registrationForm;

    /*
    @InjectComponent("username")
    private TextField usernameField;

    @InjectComponent("firstname")
    private TextField firstnameField;

    @InjectComponent("middlename")
    private TextField middlenameField;

    @InjectComponent("lastname")
    private TextField lastnameField;

    @InjectComponent("countryCode")
    private TextField countryCodeField;

    @InjectComponent("contactNo")
    private TextField contactNoField;

    @InjectComponent("email")
    private TextField emailField;

    @InjectComponent("password")
    private PasswordField passwordField;

    @InjectComponent("confirmPassword")
    private PasswordField confirmPasswordField;
    */

    @Persist(PersistenceConstants.FLASH)
    private boolean errorMsg;

    @Inject
    private RegisterService registerService;

    /*@Property
    private Map<Integer, String> countryCodeList = new HashMap<Integer, String>();

    void setupRender() {
        if (countryCodeList.size() == 0) {
            countryCodeList.put(91, "Demo1");
            countryCodeList.put(93, "Demo2");
        }
    }*/

    @Property
    private List<Integer> countryCodeList = new ArrayList<Integer>();

    void setupRender() {
        if (countryCodeList.size() == 0) {
            countryCodeList.add(91);
            countryCodeList.add(93);
            countryCodeList.add(355);
            countryCodeList.add(213);
            countryCodeList.add(376);
            countryCodeList.add(244);
            countryCodeList.add(672);
            countryCodeList.add(54);
            countryCodeList.add(374);
            countryCodeList.add(297);
            countryCodeList.add(61);
            countryCodeList.add(43);
            countryCodeList.add(994);
            countryCodeList.add(973);
            countryCodeList.add(880);
        }
    }

    void onValidateFromRegistrationForm() {
        if (username == null || username.trim().equals("")) {
            registrationForm.recordError("Username is required.");
            //registrationForm.recordError(usernameField, "Username is required.");
        }
        else {
            if (CheckUsernameExists.checkIfUsernameExists(username)) {
                registrationForm.recordError("Username already exists.");
                //registrationForm.recordError(usernameField, "Username already exists.");
            }
        }
        if (firstname == null || firstname.trim().equals("")) {
            registrationForm.recordError("Firstname is required.");
            //registrationForm.recordError(firstnameField, "Firstname is required.");
        }
        if (lastname == null || lastname.trim().equals("")) {
            registrationForm.recordError("Lastname is required.");
            //registrationForm.recordError(lastnameField, "Lastname is required.");
        }
        if (countryCode == 0) {
            registrationForm.recordError("Country Code is required.");
            //registrationForm.recordError(countryCodeField, "Country Code is required.");
        }
        else {
           if (String.valueOf(countryCode).length() < 1 || String.valueOf(countryCode).length() > 3) {
               registrationForm.recordError("Country Code should contain 1 to 3 digits.");
               //registrationForm.recordError(countryCodeField, "Country Code should contain 1 to 3 digits.");
           }
        }
        if (contactNo == 0) {
            registrationForm.recordError("Contact No. is required.");
            //registrationForm.recordError(contactNoField, "Contact No. is required.");
        }
        else {
            if (String.valueOf(contactNo).length() < 8 || String.valueOf(contactNo).length() > 10) {
                registrationForm.recordError("Contact No. should contain 8 to 10 digits.");
                //registrationForm.recordError(contactNoField, "Contact No. should contain 8 to 10 digits.");
            }
        }
        if (email == null || email.trim().equals("")) {
            registrationForm.recordError("Email is required.");
            //registrationForm.recordError(emailField, "Email is required.");
        }
        else {
            if (!email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
                registrationForm.recordError("Email is invalid.");
                //registrationForm.recordError(emailField, "Email is invalid.");
            }
            else {
                if (CheckEmailExists.checkIfEmailExists(email)) {
                    registrationForm.recordError("Email already used.");
                    //registrationForm.recordError(emailField, "Email already used.");
                }
            }
        }
        if (password == null || password.trim().equals("")) {
            registrationForm.recordError("Password is required.");
            //registrationForm.recordError(passwordField, "Password is required.");
        }
        else {
            if (password.length() < 8) {
                registrationForm.recordError("Password should contain at least 8 characters.");
                //registrationForm.recordError(passwordField, "Password should contain at least 8 characters.");
            }
            else {
                if (password.length() > 16) {
                    registrationForm.recordError("Password should contain at most 16 characters.");
                    //registrationForm.recordError(passwordField, "Password should contain at most 16 characters.");
                }
                else {
                    if (!password.matches("^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")) {
                        registrationForm.recordError("Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                        //registrationForm.recordError(passwordField, "Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                    }
                    else {
                        if (!ValidatePassword.validatePassword(new User(username, firstname, middlename, lastname, password))) {
                            registrationForm.recordError("Password should not contain username, firstname, middlename or lastname.");
                            //registrationForm.recordError(passwordField, "Password should not contain username, firstname, middlename or lastname.");
                        }
                    }
                }
            }
        }
        if (confirmPassword == null || confirmPassword.trim().equals("")) {
            registrationForm.recordError("Confirm Password is required.");
            //registrationForm.recordError(confirmPasswordField, "Confirm Password is required.");
        }
        else {
            if ((password.length() != confirmPassword.length()) || !password.equals(confirmPassword)) {
                registrationForm.recordError("Confirm Password should match Password.");
                //registrationForm.recordError(confirmPasswordField, "Confirm Password should match Password.");
            }
        }
    }

    Object onSuccess() {
        if (registerService.register(countryCode, contactNo, username, firstname, middlename, lastname, email, password))
            return "success";
        else errorMsg = true;
        return "register";
    }

    public boolean isErrorMsg() {
        return errorMsg;
    }
}