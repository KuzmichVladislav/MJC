package com.epam.esm.exception;

/**
 * The Class RequestValidationException wraps all unchecked standard Java exceptions
 * and enriches them with a custom error code and message.
 * Serves to receive localized messages about not found resource
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String messageKey;
    private final String messageParameter;

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
