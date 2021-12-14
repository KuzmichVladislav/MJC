package com.epam.esm.exception;

public class RequestValidationException extends RuntimeException {
    private String messageKey;
    private String messageParameter;

    public RequestValidationException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public String getMessageParameter() {
        return this.messageParameter;
    }
}
