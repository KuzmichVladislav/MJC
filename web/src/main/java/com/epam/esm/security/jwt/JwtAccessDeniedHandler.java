package com.epam.esm.security.jwt;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.JwtAuthorizationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * The Class JwtAccessDeniedHandler to handle an AccessDeniedException..
 */
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";

    private final MessageSource messageSource;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
            throws IOException, ServletException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonError = objectWriter.writeValueAsString(
                getErrorBody(new JwtAuthorizationException(ExceptionKey.ACCESS_FORBIDDEN), request.getLocale()));
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        response.getOutputStream().write(jsonError.getBytes(StandardCharsets.UTF_8));
    }

    private ExceptionResult getErrorBody(JwtAuthorizationException e, Locale locale) {
        String errorMessage = messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale);
        return new ExceptionResult(errorMessage, ErrorCode.ACCESS_FORBIDDEN.getCode());
    }
}
