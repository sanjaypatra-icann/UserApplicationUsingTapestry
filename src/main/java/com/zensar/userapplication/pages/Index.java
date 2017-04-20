package com.zensar.userapplication.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class Index {

    @Inject
    private Request request;

    public String getSuccessMsg() {
        return this.request.getParameter("successMsg");
    }
}