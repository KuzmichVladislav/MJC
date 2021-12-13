package com.epam.esm.exception;

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

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResult handle(ResourceNotFoundException e, Locale locale) {
        String errorMessage = createMessage(
                messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale),
                e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.RESOURCE_NOT_FOUND.getErrorCode());
    }

    @ExceptionHandler(RequestValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResult handle(RequestValidationException e, Locale locale) {
        String errorMessage = createMessage(
                messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale),
                e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.NOT_VALID_PARAM.getErrorCode());
    }

    private String createMessage(String message, String param) {
        return String.format(message, param);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResult handle(NoHandlerFoundException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.HANDLER_NOT_FOUND.getErrorCode());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResult handle(RuntimeException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.INTERNAL_ERROR.getErrorCode());
    }
/*
 FIXME: 12/13/2021
    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ExceptionResult handle(MethodNotAllowedException e, Locale locale) {
        return new ExceptionResult(e.getLocalizedMessage(), ErrorCode.METHOD_NOT_ALLOWED.getErrorCode());
    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ExceptionResult handle(HttpRequestMethodNotSupportedException e, Locale locale) {
        return new ExceptionResult(e.getLocalizedMessage(), ErrorCode.METHOD_NOT_ALLOWED.getErrorCode());
    }
*/

}

