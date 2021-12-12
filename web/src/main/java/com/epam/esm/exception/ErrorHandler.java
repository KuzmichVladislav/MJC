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
    protected ExceptionResult handle(RequestValidationException e, Locale locale) {
        String errorMessage = createMessage(
                messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale),
                e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.NOT_VALID_PARAM.getErrorCode());
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    protected ExceptionResult handle(RuntimeException e, Locale locale) {
//        String errorMessage = createMessage(
//                messageSource.getMessage(ExceptionKey.INTERNAL_ERROR.getKey(), new Object[]{}, locale),
//                e.getMessage());
//        return new ExceptionResult(errorMessage, ErrorCode.INTERNAL_ERROR.getErrorCode());
//    }


    private String createMessage(String message, String param) {
        return String.format(message, param);
    }
}

    /*
 FIXME: 12/12/2021
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ExceptionResult handle(NoHandlerFoundException e, Locale locale) {
        String errorMessage = createMessage(
                messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale),
                e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.HANDLER_NOT_FOUND.getErrorCode());
    }
*/
