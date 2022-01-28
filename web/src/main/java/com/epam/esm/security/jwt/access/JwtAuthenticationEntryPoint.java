package com.epam.esm.security.jwt.access;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class JwtAuthenticationEntryPoint to commence an authentication scheme.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AccessHandler accessHandler;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        accessHandler.addErrorResponse(response, request.getLocale(), HttpServletResponse.SC_UNAUTHORIZED,
                ExceptionKey.JWT_TOKEN_IS_EXPIRED_OR_INVALID, ErrorCode.UNAUTHORIZED);
    }
}