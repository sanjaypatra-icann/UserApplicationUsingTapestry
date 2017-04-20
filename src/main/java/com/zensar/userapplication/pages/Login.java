package com.zensar.userapplication.pages;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.services.LoginService;
import com.zensar.userapplication.utils.CheckUsernameExists;
import com.zensar.userapplication.utils.GetUserDetails;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.internal.services.LinkImpl;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.*;

import java.io.IOException;
import java.net.URL;

public class Login {

    @SessionState
    @Property
    private User loggedInUser;

    @Property
    @Validate("required, regexp=^[a-zA-Z0-9]+$")
    private String username;

    @Property
    @Validate("required, minlength=8, maxlength=16, regexp=^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")
    private String password;

    @InjectComponent("loginForm")
    private Form loginForm;

    /*
    @InjectComponent("username")
    private TextField usernameField;

    @InjectComponent("password")
    private PasswordField passwordField;
    */

    @Persist(PersistenceConstants.FLASH)
    private boolean errorMsg;

    @Inject
    private LoginService loginService;
/*

    @Inject
    private ApplicationStateManager applicationStateManager;

    @Inject
    private Request request;

    @Inject
    private Response response;

    @Inject
    PageRenderLinkSource pageRenderLinkSource;

    void setupRender() {
        System.out.println("setupRender");
        Session session = request.getSession(false);
        if (session != null) {
            System.out.println(session);
            if (loggedInUser != null) {
                System.out.println(applicationStateManager.get(User.class));
                try {
                    Link redirectTo = pageRenderLinkSource.createPageRenderLinkWithContext(Index.class);
                    response.sendRedirect(redirectTo);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
*/

    void onValidateFromLoginForm() {
        if (username == null || username.trim().equals("")) {
            loginForm.recordError("Username is required.");
            //loginForm.recordError(usernameField, "Username is required.");
        }
        else {
            if (!CheckUsernameExists.checkIfUsernameExists(username)) {
                loginForm.recordError("Username does not exist.");
                //loginForm.recordError(usernameField, "Username does not exist.");
            }
        }
        if (password == null || password.trim().equals("")) {
            loginForm.recordError("Password is required.");
            //loginForm.recordError(passwordField, "Password is required.");
        }
        else {
            if (password.length() < 8) {
                loginForm.recordError("Password should contain at least 8 characters.");
                //loginForm.recordError(passwordField, "Password should contain at least 8 characters.");
            }
            else {
                if (password.length() > 16) {
                    loginForm.recordError("Password should contain at most 16 characters.");
                    //loginForm.recordError(passwordField, "Password should contain at most 16 characters.");
                }
                else {
                    if (!password.matches("^((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,16})$")) {
                        loginForm.recordError("Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                        //loginForm.recordError(passwordField, "Password should contain at least one uppercase letter, one lowercase letter, one digit and one special symbol.");
                    }
                }
            }
        }
    }

    Object onSuccess() {
        if (loginService.authenticate(username, password)) {
            loggedInUser = GetUserDetails.getUserDetails(username);
            return "dashboard";
        }
        else    errorMsg = true;
        return "login";
    }

    public boolean isErrorMsg() {
        return errorMsg;
    }
}