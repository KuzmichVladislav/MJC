package com.epam.esm.exception;

/**
 * The Class RequestValidationException wraps all unchecked standard Java exceptions
 * and enriches them with a custom error code and message.
 * Serves to receive localized messages about request parameters validation errors.
 */
public class RequestValidationException extends RuntimeException {

    private String messageKey;
    private String messageParameter;

    public RequestValidationException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    public RequestValidationException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public String getMessageParameter() {
        return this.messageParameter;
    }
}
