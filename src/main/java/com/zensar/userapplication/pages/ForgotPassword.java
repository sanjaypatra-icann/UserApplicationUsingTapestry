package com.zensar.userapplication.pages;

import com.zensar.userapplication.services.ForgotPasswordService;
import com.zensar.userapplication.utils.CheckEmailRegistered;
import com.zensar.userapplication.utils.CheckUsernameExists;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ForgotPassword {

    @Property
    @Validate("required, regexp=^[a-zA-Z0-9]+$")
    private String username;

    @Property
    @Validate("required, email")
    private String email;

    @InjectComponent("forgotPasswordForm")
    private Form forgotPasswordForm;

    /*
    @InjectComponent("username")
    private TextField usernameField;

    @InjectComponent("email")
    private TextField emailField;
    */

    @Persist(PersistenceConstants.FLASH)
    private boolean errorMsg;

    @Persist(PersistenceConstants.FLASH)
    private boolean successMsg;

    @Inject
    private ForgotPasswordService forgotPasswordService;

    void onValidateFromForgotPasswordForm() {
        if (username == null || username.trim().equals("")) {
            forgotPasswordForm.recordError("Username is required.");
            //forgotPasswordForm.recordError(usernameField, "Username is required.");
        }
        else {
            if (!CheckUsernameExists.checkIfUsernameExists(username)) {
                forgotPasswordForm.recordError("Username does not exist.");
                //forgotPasswordForm.recordError(usernameField, "Username does not exist.");
            }
        }
        if (email == null || email.trim().equals("")) {
            forgotPasswordForm.recordError("Email is required.");
            //forgotPasswordForm.recordError(emailField, "Email is required.");
        }
        else {
            if (!email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
                forgotPasswordForm.recordError("Email is invalid.");
                //forgotPasswordForm.recordError(emailField, "Email is invalid.");
            }
        }
        if (username != null && !username.trim().equals("") && CheckUsernameExists.checkIfUsernameExists(username) && email != null && !email.trim().equals("") && email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
            if (!CheckEmailRegistered.checkIfEmailIsRegistered(username, email)) {
                forgotPasswordForm.recordError("Email is not registered.");
                //forgotPasswordForm.recordError(emailField, "Email is not registered.");
            }
        }
    }

    Object onSuccess() {
        if (forgotPasswordService.getPasswordResetLink(username, email)) {
            successMsg = true;
            errorMsg = false;
        }
        else {
            errorMsg = true;
            successMsg = false;
        }
        return "forgotpassword";
    }

    public boolean isErrorMsg() {
        return errorMsg;
    }

    public boolean isSuccessMsg() {
        return successMsg;
    }
}