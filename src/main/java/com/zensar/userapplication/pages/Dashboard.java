package com.zensar.userapplication.pages;

import com.zensar.userapplication.beans.User;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class Dashboard {

    @SessionState
    @Property
    private User loggedInUser;

    @Inject
    private Request request;

    public String getSuccessMsg() {
        return this.request.getParameter("successMsg");
    }
}