package com.zensar.userapplication.exceptions;

public class ServicesDownException extends Exception {
    public ServicesDownException() {}
    public ServicesDownException(String message) {
        super(message);
    }
    public ServicesDownException(String message, Throwable cause) {
        super(message, cause);
    }
    public ServicesDownException(Throwable cause) {
        super(cause);
    }
    public ServicesDownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
