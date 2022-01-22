package com.epam.esm.security.exception;

public class _JwtAuthenticationException extends RuntimeException {

    public _JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public _JwtAuthenticationException(String msg) {
        super(msg);
    }
}
