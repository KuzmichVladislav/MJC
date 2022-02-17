package com.epam.esm.exception;

/**
 * The Class JwtAuthorizationException wraps all unchecked standard Java exceptions
 * and enriches them with a custom error code and message.
 * Serves to receive localized messages about request parameters validation errors.
 */
public class JwtAuthorizationException extends RuntimeException {

    private final String messageKey;

    public JwtAuthorizationException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return this.messageKey;
    }
}
