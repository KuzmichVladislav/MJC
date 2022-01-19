package com.epam.esm.service;

import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
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

    User loadUserByUsername(String login);

    UserDto add(UserDto userDto);

    UserDto findByLoginAndPassword(String login, String password);
}
