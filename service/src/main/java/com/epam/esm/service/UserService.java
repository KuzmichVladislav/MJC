package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findById(String id);

    List<UserDto> findAll();
}
