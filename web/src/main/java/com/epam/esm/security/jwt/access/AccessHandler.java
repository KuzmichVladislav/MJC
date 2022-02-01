package com.epam.esm.security.jwt.access;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.JwtAuthorizationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * The Class AccessHandler to add error to response body.
 */
@Component
@RequiredArgsConstructor
public class AccessHandler {

    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";
    private final MessageSource messageSource;

    /**
     * Add error body to response.
     *
     * @param response   the http servlet response
     * @param locale     the locale
     * @param status     the http status
     * @param messageKey the error message
     * @param errorCode  the error code
     * @return the http servlet response
     * @throws IOException the io exception
     */
    public HttpServletResponse addErrorResponse(HttpServletResponse response, Locale locale, int status, String messageKey, ErrorCode errorCode) throws IOException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        JwtAuthorizationException exception = new JwtAuthorizationException(messageKey);
        String errorMessage = messageSource.getMessage(exception.getMessageKey(), new Object[]{}, locale);
        ExceptionResult exceptionResult = new ExceptionResult(errorMessage, errorCode.getCode());
        String jsonError = objectWriter.writeValueAsString(exceptionResult);
        response.setStatus(status);
        response.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        response.getOutputStream().write(jsonError.getBytes(StandardCharsets.UTF_8));
        return response;
    }
}
