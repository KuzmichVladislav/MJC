package com.epam.esm.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

/**
 * The Class ErrorHandler maps exceptions to HTTP responses
 */
@RestControllerAdvice
public class ErrorHandler {

    private final MessageSource messageSource;

    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle ResourceNotFoundException.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResult handle(ResourceNotFoundException e, Locale locale) {
        String errorMessage = createMessage(locale, e.getMessageKey(), e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.RESOURCE_NOT_FOUND.getCode());
    }

    /**
     * Handle RequestValidationException.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler(RequestValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResult handle(RequestValidationException e, Locale locale) {
        String errorMessage = createMessage(locale, e.getMessageKey(), e.getMessageParameter());
        return new ExceptionResult(errorMessage, ErrorCode.NOT_VALID_PARAM.getCode());
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResult handle(NoHandlerFoundException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.HANDLER_NOT_FOUND.getCode());
    }
// TODO: 12/23/2021  
//    /**
//     * Handle RuntimeException.
//     *
//     * @param e      the exception
//     * @param locale the locale
//     * @return the exception result
//     */
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ExceptionResult handle(RuntimeException e, Locale locale) {
//        return new ExceptionResult(e.getMessage(), ErrorCode.INTERNAL_ERROR.getCode());
//    }

    /**
     * Handle HttpRequestMethodNotSupportedException.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ExceptionResult handle(HttpRequestMethodNotSupportedException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.METHOD_NOT_ALLOWED.getCode());
    }

    /**
     * Handle HttpMediaTypeNotAcceptableException.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ExceptionResult handle(HttpMediaTypeNotSupportedException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.UNSUPPORTED_MEDIA_TYPE.getCode());
    }

    private String createMessage(Locale locale, String messageKey, String messageParameter) {
        return String.format(messageSource.getMessage(messageKey, new Object[]{}, locale),
                messageParameter);
    }
}
