package com.epam.esm.service;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import org.springframework.hateoas.PagedModel;

/**
 * The Interface UserService.
 * A interface to define all required methods for user DTO object.
 */
public interface UserService {

    /**
     * Find user by identifier.
     *
     * @param id the identifier
     * @return the user DTO
     */
    UserDto findById(long id);


    /**
     * Find all users.
     *
     * @param queryParameterDto the query parameter DTO
     * @return the page
     */
    PagedModel<UserDto> findAll(QueryParameterDto queryParameterDto);

    UserRegistrationDto loadUserByUsername(String username);

    UserDto add(UserRegistrationDto registrationDto);

    UserRegistrationDto findByUsernameAndPassword(String username, String password);
}
