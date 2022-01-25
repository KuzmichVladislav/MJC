package com.epam.esm.security.entity;

import com.epam.esm.dto.UserRegistrationDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * The Class JwtUserDetails provides core user information..
 */
public class JwtUserDetails implements UserDetails {

    private long userId;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Map user registration DTO to jwt user details.
     *
     * @param userRegistrationDto the user registration dto
     * @return the jwt user details
     */
    public static JwtUserDetails mapToJwtUserDetails(UserRegistrationDto userRegistrationDto) {
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        jwtUserDetails.userId = userRegistrationDto.getId();
        jwtUserDetails.username = userRegistrationDto.getUsername();
        jwtUserDetails.password = userRegistrationDto.getPassword();
        jwtUserDetails.authorities = userRegistrationDto.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return jwtUserDetails;
    }

    public long getUserId() {
        return userId;
    }

    public JwtUserDetails setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}