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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * The Class JwtAuthenticationEntryPoint to commence an authentication scheme.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";

    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonError = objectWriter.writeValueAsString(
                getErrorBody(new JwtAuthorizationException(ExceptionKey.JWT_TOKEN_IS_EXPIRED_OR_INVALID),
                        request.getLocale()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        response.getOutputStream().write(jsonError.getBytes(StandardCharsets.UTF_8));
    }

    private ExceptionResult getErrorBody(JwtAuthorizationException e, Locale locale) {
        String errorMessage = messageSource.getMessage(e.getMessageKey(), new Object[]{}, locale);
        return new ExceptionResult(errorMessage, ErrorCode.UNAUTHORIZED.getCode());
    }
}