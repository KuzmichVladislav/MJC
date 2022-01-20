package com.epam.esm.security.entity;

import com.epam.esm.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class JwtUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean active;

    public static JwtUserDetails mapToJwtUserDetails(UserDto userDto) {
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        jwtUserDetails.username = userDto.getUsername();
        jwtUserDetails.password = userDto.getPassword();
        jwtUserDetails.authorities = userDto.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        jwtUserDetails.active = userDto.isActive();
        return jwtUserDetails;
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
        return active;
    }
}