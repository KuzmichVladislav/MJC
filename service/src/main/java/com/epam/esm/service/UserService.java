package com.epam.esm.service;

import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;

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
    PageWrapper<UserDto> findAll(QueryParameterDto queryParameterDto);
}
