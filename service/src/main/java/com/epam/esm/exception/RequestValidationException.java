package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestValidationException extends RuntimeException {
    private String messageKey;
    private String messageParameter;
}
