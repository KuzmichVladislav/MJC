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
        System.out.println(e.getMessageKey());
        String errorMessage = createMessage(
                messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale),
                e.getMessageParameter());
        return ExceptionResult.builder()
                .errorMessage(errorMessage)
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND.getErrorCode())
                .build();
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    protected ExceptionResult handleHttpBusinessException(ValidationException ex) {
        return ExceptionResult.builder().errorMessage(ex.getMessage()).errorCode(ErrorCode.NOT_VALID_PARAM.getErrorCode()).build();
    }

    private String createMessage(String message, String param) {
        return String.format(message, param);
    }
}
