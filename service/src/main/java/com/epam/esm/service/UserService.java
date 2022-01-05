package com.epam.esm.service;

import com.epam.esm.dto.PageWrapper;
import com.epam.esm.dto.QueryParameterDto;
import com.epam.esm.dto.UserDto;

public interface UserService {

    UserDto findById(long id);

    PageWrapper<UserDto> findAll(QueryParameterDto queryParameterDto);
}
