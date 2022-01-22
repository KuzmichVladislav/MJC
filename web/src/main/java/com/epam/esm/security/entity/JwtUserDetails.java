package com.epam.esm.security.entity;

import com.epam.esm.dto.UserRegistrationDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class JwtUserDetails implements UserDetails {

    private long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static JwtUserDetails mapToJwtUserDetails(UserRegistrationDto userRegistrationDto) {
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        jwtUserDetails.id = userRegistrationDto.getId();
        jwtUserDetails.username = userRegistrationDto.getUsername();
        jwtUserDetails.password = userRegistrationDto.getPassword();
        jwtUserDetails.authorities = userRegistrationDto.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return jwtUserDetails;
    }

    public long getId() {
        return id;
    }

    public JwtUserDetails setId(long id) {
        this.id = id;
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