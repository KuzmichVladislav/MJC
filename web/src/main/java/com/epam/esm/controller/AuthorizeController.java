package com.epam.esm.controller;

import com.epam.esm.dto.AuthorizeRequestDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * The Class AuthorizeController is a Controller class which will have
 * end points for authorization and registration user.
 */
@Controller
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthorizeController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Register user.
     *
     * @param userRegistrationDto the user registration DTO
     * @return the user DTO
     */
    @PostMapping("/register")
    public UserDto registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return userService.add(userRegistrationDto);
    }

    /**
     * Authorize user.
     *
     * @param authorizeRequestDto the authorize request DTO
     * @return the JWT token
     */
    @PostMapping("/authorize")
    public String authorizeUser(@RequestBody AuthorizeRequestDto authorizeRequestDto) {
        UserDto userEntity = userService.findByUsernameAndPassword(authorizeRequestDto.getUsername(),
                authorizeRequestDto.getPassword());
        return jwtTokenProvider.generateToken(userEntity.getUsername());
    }
}
