package com.epam.esm.security.jwt.filter;

import com.epam.esm.security.entity.JwtUserDetails;
import com.epam.esm.security.service.JwtUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

/**
 * The Class JwtTokenProvider serves to work with Jwt token.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private final JwtUserDetailsService jwtUserDetailsService;
    @Value("$(jwt.secret)")
    private String jwtSecret;
    @Value("${jwt.expired}")
    private long expirationInMilliseconds;

    /**
     * Generate Jwt token.
     *
     * @param username the username
     * @return the Jwt token
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationInMilliseconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Validate Jwt token.
     *
     * @param token the Jwt token
     * @return the boolean
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Gets username from Jwt token.
     *
     * @param token the Jwt token
     * @return the username from Jwt token
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Resolve Jwt token.
     *
     * @param request the request
     * @return the bearer
     */
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(BEARER)) {
            return bearer.substring(7);
        }
        return null;
    }

    /**
     * Gets authentication.
     *
     * @param token the Jwt token
     * @return the authentication
     */
    public Authentication getAuthentication(String token) {
        JwtUserDetails jwtUserDetails = jwtUserDetailsService.loadUserByUsername(getUsernameFromToken(token));
        return new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
    }
}
