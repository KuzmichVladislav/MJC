package com.epam.esm.controller;

import com.epam.esm.dto.AuthorizeRequestDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthorizeController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return userService.add(userRegistrationDto);
    }

    @PostMapping("/authorize")
    public String authorizeUser(@RequestBody AuthorizeRequestDto authorizeRequestDto) {
        UserDto userEntity = userService.findByUsernameAndPassword(authorizeRequestDto.getUsername(),
                authorizeRequestDto.getPassword());
        return jwtTokenProvider.generateToken(userEntity.getUsername());
    }
}
