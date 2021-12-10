package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResult {
    private String errorMessage;
    private int errorCode;
}
