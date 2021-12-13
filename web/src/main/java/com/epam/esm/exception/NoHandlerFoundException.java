package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoHandlerFoundException extends RuntimeException {
    private String messageKey;
    private String messageParameter;

    public NoHandlerFoundException() {
        super();
    }

    public NoHandlerFoundException(String message) {
        super(message);
    }

    public NoHandlerFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoHandlerFoundException(Throwable cause) {
        super(cause);
    }
}