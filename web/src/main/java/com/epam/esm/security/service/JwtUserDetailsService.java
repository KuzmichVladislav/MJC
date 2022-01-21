package com.epam.esm.security.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.security.entity.JwtUserDetails;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRegistrationDto userRegistrationDto = userService.loadUserByUsername(username);
        return JwtUserDetails.mapToJwtUserDetails(userRegistrationDto);
    }
}