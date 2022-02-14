package com.epam.esm.security.jwt.access;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Class JwtAccessDeniedHandler to handle an AccessDeniedException.
 */
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final AccessHandler accessHandler;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
            throws IOException {
        accessHandler.addErrorResponse(response, request.getLocale(), HttpServletResponse.SC_FORBIDDEN,
                ExceptionKey.ACCESS_FORBIDDEN, ErrorCode.ACCESS_FORBIDDEN);
    }
}
