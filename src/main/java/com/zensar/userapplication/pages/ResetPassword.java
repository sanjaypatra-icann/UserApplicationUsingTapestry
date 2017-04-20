package com.zensar.userapplication.pages;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.serviceprovider.ServiceProvider;
import com.zensar.userapplication.services.ResetPasswordService;
import com.zensar.userapplication.utils.CheckUsernameExists;
import com.zensar.userapplication.utils.EncryptPassword;
import com.zensar.userapplication.utils.GetUserDetails;
import com.zensar.userapplication.utils.ValidatePassword;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class ResetPassword {

    @Inject
    private Request request;

    public String getUsername() {
        return this.request.getParameter("username");
    }

    private String username = getUsername();

    @Property
    @Validate("required, minlength=8, maxlength=16, regexp=^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")
    private String newPassword;

    @Property
    @Validate("required")
    private String confirmPassword;

    @InjectComponent("resetPasswordForm")
    private Form resetPasswordForm;

    /*
    @InjectComponent("newPassword")
    private PasswordField newPasswordField;

    @InjectComponent("confirmPassword")
    private PasswordField confirmPasswordField;
    */

    @Persist(PersistenceConstants.FLASH)
    private boolean errorMsg;

    @Persist(PersistenceConstants.FLASH)
    private boolean successMsg;

    @Inject
    private ResetPasswordService resetPasswordService;

    void onValidateFromResetPasswordForm() {
        User user = GetUserDetails.getUserDetails(username);
        if (username == null || username.trim().equals("")) {
            resetPasswordForm.recordError("Username is required.");
            //resetPasswordForm.recordError(usernameField, "Username is required.");
        }
        else {
            if (!CheckUsernameExists.checkIfUsernameExists(username)) {
                resetPasswordForm.recordError("Username does not exist.");
                //resetPasswordForm.recordError(usernameField, "Username does not exist.");
            }
        }
        if (newPassword == null || newPassword.trim().equals("")) {
            resetPasswordForm.recordError("New Password is required.");
            //resetPasswordForm.recordError(newPasswordField, "New Password is required.");
        }
        else {
            if (user.getPassword().equals(EncryptPassword.getEncryptedPassword(newPassword))) {
                resetPasswordForm.recordError("New Password should not be the current password.");
                //resetPasswordForm.recordError(newPasswordField, "New Password should not be the current password.");
            }
            else {
                if (ValidatePassword.checkPasswordInRecentPasswords(username, EncryptPassword.getEncryptedPassword(newPassword))) {
                    String errorMessage = "New Password should not be last ";
                    if (ServiceProvider.getNoOfLastPasswords() > 1)
                        errorMessage += ServiceProvider.getNoOfLastPasswords() + " passwords.";
                    else errorMessage += "password.";
                    resetPasswordForm.recordError(errorMessage);
                    //resetPasswordForm.recordError(newPasswordField, errorMessage);
                }
                else {
                    if (newPassword.length() < 8) {
                        resetPasswordForm.recordError("New Password should contain at least 8 characters.");
                        //resetPasswordForm.recordError(newPasswordField, "New Password should contain at least 8 characters.");
                    }
                    else {
                        if (newPassword.length() > 16) {
                            resetPasswordForm.recordError("New Password should contain at most 16 characters.");
                            //resetPasswordForm.recordError(newPasswordField, "New Password should contain at most 16 characters.");
                        }
                        else {
                            if (!newPassword.matches("^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")) {
                                resetPasswordForm.recordError("New Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                                //resetPasswordForm.recordError(newPasswordField, "New Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                            }
                            else {
                                if (!ValidatePassword.validatePassword(new User(username, user.getFirstname(), user.getMiddlename(), user.getLastname(), newPassword))) {
                                    resetPasswordForm.recordError("New Password should not contain username, firstname, middlename or lastname.");
                                    //resetPasswordForm.recordError(newPasswordField, "New Password should not contain username, firstname, middlename or lastname.");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (confirmPassword == null || confirmPassword.trim().equals("")) {
            resetPasswordForm.recordError("Confirm Password is required.");
            //resetPasswordForm.recordError(confirmPasswordField, "Confirm Password is required.");
        }
        else {
            if ((newPassword.length() != confirmPassword.length()) || !newPassword.equals(confirmPassword)) {
                resetPasswordForm.recordError("Confirm Password should match New Password.");
                //resetPasswordForm.recordError(confirmPasswordField, "Confirm Password should match New Password.");
            }
        }
    }

    Object onSuccess() {
        if (resetPasswordService.resetPassword(username, newPassword)) {
            successMsg = true;
            errorMsg = false;
        }
        else {
            errorMsg = true;
            successMsg = false;
        }
        return "resetpassword";
    }

    public boolean isErrorMsg() {
        return errorMsg;
    }

    public boolean isSuccessMsg() {
        return successMsg;
    }
}