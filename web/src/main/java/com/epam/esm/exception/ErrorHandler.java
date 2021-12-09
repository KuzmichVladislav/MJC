package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResult handle(ResourceNotFoundException e, Locale locale) {
        String errorMessage = messageSource.getMessage("exception.resource_not_found", new Object[]{}, locale);
        return ExceptionResult.builder()
                .errorMessage(errorMessage)
                .errorCode(ErrorCode.resourceNotFound.getErrorCode())
                .build();
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    protected ExceptionResult handleHttpBusinessException(ValidationException ex) {
        return ExceptionResult.builder().errorMessage(ex.getMessage()).errorCode(ErrorCode.notValidParam.getErrorCode()).build();
    }
}
