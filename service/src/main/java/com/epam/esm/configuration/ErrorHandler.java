package com.epam.esm.configuration;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    final ExceptionResult result;

    public ErrorHandler(ExceptionResult result) {
        this.result = result;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ExceptionResult handleHttpBusinessException(ResourceNotFoundException ex) {
        return ExceptionResult.builder().errorMessage(ex.getMessage()).errorCode(ErrorCode.resourceNotFound.getErrorCode()).build();
    }
}
