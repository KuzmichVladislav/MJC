package com.epam.esm.exception;

public class PasswordAuthenticationException extends RuntimeException {

    private String messageKey;
    private String messageParameter;

    public PasswordAuthenticationException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    public PasswordAuthenticationException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public String getMessageParameter() {
        return this.messageParameter;
    }
}
