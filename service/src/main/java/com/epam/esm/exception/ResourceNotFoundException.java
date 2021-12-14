package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String messageKey;
    private String messageParameter;

    public ResourceNotFoundException(String messageKey, String messageParameter) {
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
