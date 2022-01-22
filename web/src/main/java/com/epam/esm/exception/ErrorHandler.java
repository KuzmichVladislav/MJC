package com.epam.esm.exception;

import com.epam.esm.security.exception._JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * The Class ErrorHandler maps exceptions to HTTP responses
 */
@RestControllerAdvice
public class ErrorHandler {

    private final MessageSource messageSource;

    /**
     * Instantiates a new Error handler.
     *
     * @param messageSource the message source
     */
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle exception result.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResult handle(MethodArgumentNotValidException e, Locale locale) {
        StringBuilder errorMessage = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(error -> errorMessage.append(createMessage(locale, error.getDefaultMessage(),
                String.valueOf(((FieldError) error).getRejectedValue()))));
        return new ExceptionResult(errorMessage.toString(), ErrorCode.NOT_VALID_PARAM.getCode());
    }

    /**
     * Handle exception result.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResult handle(ConstraintViolationException e, Locale locale) {
        List<String> collect = e.getConstraintViolations()
                .stream()
                .map(error -> createMessage(locale, error.getMessageTemplate(), String.valueOf(error.getInvalidValue())))
                .collect(Collectors.toList());
        return new ExceptionResult(collect.get(0), ErrorCode.NOT_VALID_PARAM.getCode());
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

    /**
     * Handle RuntimeException.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
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

    /**
     * Handle exception result.
     *
     * @param e      the exception
     * @param locale the locale
     * @return the exception result
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResult handle(MethodArgumentTypeMismatchException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.NOT_VALID_PARAM.getCode());
    }

    private String createMessage(Locale locale, String messageKey, String messageParameter) {
        return String.format(messageSource.getMessage(messageKey, new Object[]{}, locale),
                messageParameter);
    }

    private String createMessage(Locale locale, String messageKey) {
        return messageSource.getMessage(messageKey, new Object[]{}, locale);
    }

    @ExceptionHandler(_JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResult handle(_JwtAuthenticationException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.UNAUTHORIZED.getCode());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResult handle(ExpiredJwtException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.UNAUTHORIZED.getCode());
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResult handle(JwtException e, Locale locale) {
        return new ExceptionResult(e.getMessage(), ErrorCode.UNAUTHORIZED.getCode());
    }

    // TODO: 1/22/2022
    @ExceptionHandler(PasswordAuthenticationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResult handle(PasswordAuthenticationException e, Locale locale) {
        String errorMessage = createMessage(locale, e.getMessageKey());
        return new ExceptionResult(errorMessage, ErrorCode.RESOURCE_NOT_FOUND.getCode());
    }
}
