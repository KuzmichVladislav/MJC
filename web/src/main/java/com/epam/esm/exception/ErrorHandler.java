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

    private final MessageSource messageSource;

    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResult handle(ResourceNotFoundException e, Locale locale) {
        String errorMessage = createMessage(
                messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale),
                e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.RESOURCE_NOT_FOUND.getErrorCode());
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ExceptionResult handle(ValidationException e, Locale locale) {
        String errorMessage = createMessage(
                messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale),
                e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.NOT_VALID_PARAM.getErrorCode());
    }

    private String createMessage(String message, String param) {
        return String.format(message, param);
    }
}
