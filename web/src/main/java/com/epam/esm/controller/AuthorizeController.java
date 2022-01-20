package com.epam.esm.controller;

import com.epam.esm.dto.AuthorizeRequestDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthorizeController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody @Valid UserDto userDto) { // TODO: 1/20/2022 add validation
        userService.add(userDto);
        return "OK";
        // TODO: 1/19/2022 redirect?
    }

    @PostMapping("/authorize")
    public String authorizeUser(@RequestBody AuthorizeRequestDto authorizeRequestDto) {
        UserDto userEntity = userService.findByUsernameAndPassword(authorizeRequestDto.getUsername(), authorizeRequestDto.getPassword());
        return jwtTokenProvider.generateToken(userEntity.getUsername());
    }
}
