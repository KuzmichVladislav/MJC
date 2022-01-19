package com.epam.esm.service;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The Interface UserService.
 * A interface to define all required methods for user DTO object.
 */
public interface UserService extends UserDetailsService {

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

//    Optional<User> findByUserLogin(String login);

    UserDto add(UserDto userDto);
}
