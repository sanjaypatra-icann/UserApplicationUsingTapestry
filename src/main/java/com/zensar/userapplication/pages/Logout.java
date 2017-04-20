package com.zensar.userapplication.pages;

import com.zensar.userapplication.beans.User;
import com.zensar.userapplication.utils.GetUserDetails;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Session;

public class Logout {

    @Inject
    private Request request;

    @Inject
    private ApplicationStateManager applicationStateManager;

    void pageLoaded() {
        applicationStateManager.set(User.class, null);
        Session session = request.getSession(false);
        if (session != null)
            try {
                session.invalidate();
            } catch (Exception e) {
                System.err.print("Session invalidate error " + e.getMessage());
            }
    }
}