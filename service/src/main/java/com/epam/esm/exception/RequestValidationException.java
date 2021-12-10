package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestValidationException extends RuntimeException {

    private String messageKey;
    private String messageParameter;

    public RequestValidationException() {
        super();
    }

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestValidationException(Throwable cause) {
        super(cause);
    }
}
