package com.zensar.userapplication.pages;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.services.ModifyAccountDetailsService;
import com.zensar.userapplication.utils.CheckEmailExists;
import com.zensar.userapplication.utils.CheckUsernameExists;
import com.zensar.userapplication.utils.GetUserDetails;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;

import java.util.ArrayList;
import java.util.List;

public class ModifyAccountDetails {

    @SessionState
    @Property
    private User loggedInUser;

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
    @Validate("required, minlength=10, maxlength=10, regexp=^[0-9]{10}$")
    private long contactNo;

    @Property
    @Validate("required, email")
    private String email;

    @InjectComponent("modifyAccountDetailsForm")
    private Form modifyAccountDetailsForm;

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
    */

    @Persist(PersistenceConstants.FLASH)
    private boolean errorMsg;

    @Persist(PersistenceConstants.FLASH)
    private boolean successMsg;

    @Inject
    private ModifyAccountDetailsService modifyAccountDetailsService;

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
    @Inject
    private ApplicationStateManager applicationStateManager;

    void onPrepare() {
        username = loggedInUser.getUsername();
        firstname = loggedInUser.getFirstname();
        middlename = loggedInUser.getMiddlename();
        lastname = loggedInUser.getLastname();
        countryCode = loggedInUser.getCountryCode();
        contactNo = loggedInUser.getContactNo();
        email = loggedInUser.getEmail();
    }

    void onValidateFromModifyAccountDetailsForm() {
        if (username == null || username.trim().equals("")) {
            modifyAccountDetailsForm.recordError("Username is required.");
            //modifyAccountDetailsForm.recordError(usernameField, "Username is required.");
        }
        else {
            if (!CheckUsernameExists.checkIfUsernameExists(username)) {
                modifyAccountDetailsForm.recordError("Username does not exist.");
                //modifyAccountDetailsForm.recordError(usernameField, "Username does not exist.");
            }
        }
        if (firstname == null || firstname.trim().equals("")) {
            modifyAccountDetailsForm.recordError("Firstname is required.");
            //modifyAccountDetailsForm.recordError(firstnameField, "Firstname is required.");
        }
        if (lastname == null || lastname.trim().equals("")) {
            modifyAccountDetailsForm.recordError("Lastname is required.");
            //modifyAccountDetailsForm.recordError(lastnameField, "Lastname is required.");
        }
        if (countryCode == 0) {
            modifyAccountDetailsForm.recordError("Country Code is required.");
            //modifyAccountDetailsForm.recordError(countryCodeField, "Country Code is required.");
        }
        else {
            if (String.valueOf(countryCode).length() < 2 || String.valueOf(countryCode).length() > 3) {
                modifyAccountDetailsForm.recordError("Country Code should contain 2 to 3 digits.");
                //modifyAccountDetailsForm.recordError(countryCodeField, "Country Code should contain 2 to 3 digits.");
            }
        }
        if (contactNo == 0) {
            modifyAccountDetailsForm.recordError("Contact No. is required.");
            //modifyAccountDetailsForm.recordError(contactNoField, "Contact No. is required.");
        }
        else {
            if (String.valueOf(contactNo).length() < 8 || String.valueOf(contactNo).length() > 10) {
                modifyAccountDetailsForm.recordError("Contact No. should contain 8 to 10 digits.");
                //modifyAccountDetailsForm.recordError(contactNoField, "Contact No. should contain 8 to 10 digits.");
            }
        }
        if (email == null || email.trim().equals("")) {
            modifyAccountDetailsForm.recordError("Email is required.");
            //modifyAccountDetailsForm.recordError(emailField, "Email is required.");
        }
        else {
            if (!email.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")) {
                modifyAccountDetailsForm.recordError("Email is invalid.");
                //modifyAccountDetailsForm.recordError(emailField, "Email is invalid.");
            }
            else {
                if (CheckEmailExists.checkIfEmailIsUsedByOtherUser(username, email)) {
                    modifyAccountDetailsForm.recordError("Email already used.");
                    //modifyAccountDetailsForm.recordError(emailField, "Email already used.");
                }
            }
        }
    }

    Object onSuccess() {
        if (modifyAccountDetailsService.modifyAccountDetails(countryCode, contactNo, username, firstname, middlename, lastname, email)) {
            successMsg = true;
            errorMsg = false;
            applicationStateManager.set(User.class, GetUserDetails.getUserDetails(username));
        }
        else {
            errorMsg = true;
            successMsg = false;
        }
        return "modifyaccountdetails";
    }

    public boolean isErrorMsg() {
        return errorMsg;
    }

    public boolean isSuccessMsg() {
        return successMsg;
    }
}