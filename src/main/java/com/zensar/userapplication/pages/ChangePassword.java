package com.zensar.userapplication.pages;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.serviceprovider.ServiceProvider;
import com.zensar.userapplication.services.ChangePasswordService;
import com.zensar.userapplication.utils.CheckUsernameExists;
import com.zensar.userapplication.utils.EncryptPassword;
import com.zensar.userapplication.utils.GetUserDetails;
import com.zensar.userapplication.utils.ValidatePassword;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;

public class ChangePassword {

    @SessionState
    @Property
    private User loggedInUser;

    @Property
    @Validate("required, regexp=^[a-zA-Z0-9]+$")
    private String username = loggedInUser.getUsername();

    @Property
    @Validate("required, minlength=8, maxlength=16, regexp=^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")
    private String currentPassword;

    @Property
    @Validate("required, minlength=8, maxlength=16, regexp=^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")
    private String newPassword;

    @Property
    @Validate("required")
    private String confirmPassword;

    @InjectComponent("changePasswordForm")
    private Form changePasswordForm;

    /*
    @InjectComponent("username")
    private TextField usernameField;

    @InjectComponent("currentPassword")
    private PasswordField currentPasswordField;

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
    private ChangePasswordService changePasswordService;

    @Inject
    private ApplicationStateManager applicationStateManager;

    void onPrepare() {
        username = loggedInUser.getUsername();
    }

    void onValidateFromChangePasswordForm() {
        if (username == null || username.trim().equals("")) {
            changePasswordForm.recordError("Username is required.");
            //changePasswordForm.recordError(usernameField, "Username is required.");
        }
        else {
            if (!CheckUsernameExists.checkIfUsernameExists(username)) {
                changePasswordForm.recordError("Username does not exist.");
                //changePasswordForm.recordError(usernameField, "Username does not exist.");
            }
        }
        if (currentPassword == null || currentPassword.trim().equals("")) {
            changePasswordForm.recordError("Current Password is required.");
            //changePasswordForm.recordError(currentPasswordField, "Current Password is required.");
        }
        else {
            if (currentPassword.length() < 8) {
                changePasswordForm.recordError("Current Password should contain at least 8 characters.");
                //changePasswordForm.recordError(currentPasswordField, "Current Password should contain at least 8 characters.");
            }
            else {
                if (currentPassword.length() > 16) {
                    changePasswordForm.recordError("Current Password should contain at most 16 characters.");
                    //changePasswordForm.recordError(currentPasswordField, "Current Password should contain at most 16 characters.");
                }
                else {
                    if (!currentPassword.matches("^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")) {
                        changePasswordForm.recordError("Current Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                        //changePasswordForm.recordError(currentPasswordField, "Current Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                    }
                }
            }
        }
        if (newPassword == null || newPassword.trim().equals("")) {
            changePasswordForm.recordError("New Password is required.");
            //changePasswordForm.recordError(newPasswordField, "New Password is required.");
        }
        else {
            if (loggedInUser.getPassword().equals(EncryptPassword.getEncryptedPassword(newPassword))) {
                changePasswordForm.recordError("New Password should not be the current password.");
                //changePasswordForm.recordError(newPasswordField, "New Password should not be the current password.");
            }
            else {
                if (ValidatePassword.checkPasswordInRecentPasswords(username, EncryptPassword.getEncryptedPassword(newPassword))) {
                    String errorMessage = "New Password should not be last ";
                    if (ServiceProvider.getNoOfLastPasswords() > 1)
                        errorMessage += ServiceProvider.getNoOfLastPasswords() + " passwords.";
                    else errorMessage += "password.";
                    changePasswordForm.recordError(errorMessage);
                    //changePasswordForm.recordError(newPasswordField, errorMessage);
                }
                else {
                    if (newPassword.length() < 8) {
                        changePasswordForm.recordError("New Password should contain at least 8 characters.");
                        //changePasswordForm.recordError(newPasswordField, "New Password should contain at least 8 characters.");
                    }
                    else {
                        if (newPassword.length() > 16) {
                            changePasswordForm.recordError("New Password should contain at most 16 characters.");
                            //changePasswordForm.recordError(newPasswordField, "New Password should contain at most 16 characters.");
                        }
                        else {
                            if (!newPassword.matches("^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")) {
                                changePasswordForm.recordError("New Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                                //changePasswordForm.recordError(newPasswordField, "New Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                            }
                            else {
                                if (!ValidatePassword.validatePassword(new User(username, loggedInUser.getFirstname(), loggedInUser.getMiddlename(), loggedInUser.getLastname(), newPassword))) {
                                    changePasswordForm.recordError("New Password should not contain username, firstname, middlename or lastname.");
                                    //changePasswordForm.recordError(newPasswordField, "New Password should not contain username, firstname, middlename or lastname.");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (confirmPassword == null || confirmPassword.trim().equals("")) {
            changePasswordForm.recordError("Confirm Password is required.");
            //changePasswordForm.recordError(confirmPasswordField, "Confirm Password is required.");
        }
        else {
            if ((newPassword.length() != confirmPassword.length()) || !newPassword.equals(confirmPassword)) {
                changePasswordForm.recordError("Confirm Password should match New Password.");
                //changePasswordForm.recordError(confirmPasswordField, "Confirm Password should match New Password.");
            }
        }
    }

    Object onSuccess() {
        if (changePasswordService.changePassword(username, currentPassword, newPassword)) {
            successMsg = true;
            errorMsg = false;
            applicationStateManager.set(User.class, GetUserDetails.getUserDetails(username));
        }
        else {
            errorMsg = true;
            successMsg = false;
        }
        return "changepassword";
    }

    public boolean isErrorMsg() {
        return errorMsg;
    }

    public boolean isSuccessMsg() {
        return successMsg;
    }
}