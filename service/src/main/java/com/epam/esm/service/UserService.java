package com.epam.esm.service;

import com.epam.esm.dto.AuthorizeRequestDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The Interface UserService.
 * An interface to define all required methods for user DTO object.
 */
public interface UserService {

    /**
     * Find user by identifier.
     *
     * @param id the identifier
     * @return the user DTO object
     */
    UserDto findById(long id);

    /**
     * Find all user DTO objects.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<UserDto> findAll(Pageable pageable);

    /**
     * Load user by username.
     *
     * @param username the username
     * @return the user registration DTO object
     */
    UserRegistrationDto loadUserByUsername(String username);

    /**
     * Add user DTO object.
     *
     * @param registrationDto the registration DTO object
     * @return the user DTO object
     */
    UserDto add(UserRegistrationDto registrationDto);

    /**
     * Find by user username and password.
     *
     * @param authorizeRequestDto the authorize request DTO object
     * @return the user registration DTO object
     */
    UserRegistrationDto authorize(AuthorizeRequestDto authorizeRequestDto);
}
