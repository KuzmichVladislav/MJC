package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResult {
    private String errorMessage;
    private int errorCode;
}
